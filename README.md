# News_App
Applicazione per visualizzare le news in tempo reale.

L'Api disponibili sono quelle di The News API (https://www.thenewsapi.com/) che permetto di ricavare nel news da tutto il Mondo.

L'applicaizone consente di visualizzare, nella scehramata iniziale, la lista delle notizie con immagine, titolo e descrizione dell'articolo.
Eseguendo un click su una specifica notizia si accede ad una seconda schermata dove è possibile leggere la news nel dettaglio.
La schermata principale si complet poi con due pulsanti di refresh e filtri posti nella parte bassa della schermata che consentono rispettivamente
di eseguire una nuova chiamata API andando ad aggiornare la visualizzazione delle nostizie e di accedere al manu dei filtri.
Il menu dei filtri mostra le tipologie e le lingue delle notizia caricate in predenza.
E' possibile selezionare la categoria/lingua desiderata per filtrare le notizie visualizzate nella scheramata principale.
E' presente inoltre il pulsante per resettare i filtri.

ToDO:
- definire il proprio token API_TOKEN per la chiamata API nella seguente classe: example\provatecnica\utils\Constants.kt
