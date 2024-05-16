package com.example.weatherxml

import NewsAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherxml.model.DataNews
import com.example.weatherxml.utils.Constants
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var newsApi: NewsApi
    private lateinit var drawer: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var filterButton: ImageButton
    private lateinit var refreshButton: ImageButton
    private lateinit var categoriaFiltroList: List<List<String>>
    private lateinit var linguaFiltroList: List<String>
    private lateinit var allItems: List<DataNews>
    private lateinit var viewModel: MainViewModel
    private lateinit var resetFilterButton: Button
    val REQUEST_SECOND_ACTIVITY = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val menu = navigationView.menu

        filterButton = findViewById(R.id.filter_button)
        refreshButton = findViewById(R.id.refresh_button)
        resetFilterButton = navigationView.getHeaderView(0).findViewById(R.id.filter_reset_button)
        drawer = findViewById(R.id.drawer)
        recyclerView = findViewById(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        newsAdapter = NewsAdapter(emptyList()) { clickedItem ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title", clickedItem.title)
            intent.putExtra("url", clickedItem.url)
            intent.putExtra("image_url", clickedItem.imageUrl)
            startActivityForResult(intent, REQUEST_SECOND_ACTIVITY)
        }
        recyclerView.adapter = newsAdapter

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        newsApi = retrofit.create(NewsApi::class.java)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            val groupId = menuItem.groupId

            when (groupId) {
                R.id.gruppo1 -> {
                    val id = menuItem.itemId
                    val elementoSelezionato = categoriaFiltroList.filter { it.isNotEmpty() }.distinct().flatten()[id]
                    filtraRisultatiCategorie(elementoSelezionato)
                }
                R.id.gruppo2 -> {
                    val id = menuItem.itemId
                    val elementoSelezionato = linguaFiltroList.distinct()[id]
                    filtraRisultatiLingua(elementoSelezionato)
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }

        val repository = NewsRepository(newsApi)
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.newsData.observe(this, Observer { newsData ->
            newsData?.let {
                newsAdapter.updateData(it)
            }
        })

        recyclerView.adapter = newsAdapter

        viewModel.newsData.observe(this, Observer { news ->
            if (news != null) {
                allItems = news
                categoriaFiltroList = news.map { it.categories }
                var listaCategorieSenzaNull = categoriaFiltroList.filter { it.isNotEmpty() }.flatten()
                listaCategorieSenzaNull = listaCategorieSenzaNull.distinct()

                linguaFiltroList = news.map { it.language }
                linguaFiltroList = linguaFiltroList.distinct()

                componiFiltriCategorie(menu, listaCategorieSenzaNull)
                componiFiltriLingue(menu, linguaFiltroList)

            } else {
                Toast.makeText(this@MainActivity, "I dati non sono stati aggiornati correttamente", Toast.LENGTH_LONG).show()
            }
        })

        lifecycleScope.launch {
            viewModel.getNews()
        }

        filterButton.setOnClickListener {
            drawer.openDrawer(GravityCompat.START)
        }
        
        refreshButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        resetFilterButton.setOnClickListener {
            viewModel.newsData.value?.let { newsList ->
                newsAdapter.updateData(newsList)
            }
            drawer.closeDrawer(GravityCompat.START)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SECOND_ACTIVITY && resultCode == Activity.RESULT_OK) {
            viewModel.newsData.value?.let { newsList ->
                newsAdapter.updateData(newsList)
            }
        }
    }

    private fun componiFiltriCategorie(menu: Menu, listaFiltri: List<String>) {
        val subMenuCategorie = menu.addSubMenu("CATEGORIE:")
        for (i in 0 until listaFiltri.size) {
            subMenuCategorie.add(R.id.gruppo1, i, Menu.NONE, listaFiltri[i])
        }
    }

    private fun componiFiltriLingue(menu: Menu, listaFiltri: List<String>) {
        val subMenuLingue = menu.addSubMenu("LINGUE:")
        for (i in 0 until listaFiltri.size) {
            subMenuLingue.add(R.id.gruppo2, i, Menu.NONE, listaFiltri[i])
        }
    }

    private fun filtraRisultatiCategorie(tipologiaFiltro: String) {
        val filteredItems = allItems.filter { tipologiaFiltro in it.categories}
        newsAdapter.updateData(filteredItems)
    }

    private fun filtraRisultatiLingua(tipologiaFiltro: String) {
        val filteredItems = allItems.filter { tipologiaFiltro in it.language}
        newsAdapter.updateData(filteredItems)
    }
}
