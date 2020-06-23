

<p align="center"><img src="https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/ic_launcher.png?raw=true" alt="Apaçık Kur'an'a andolsun!" /></p>

<h2 align="center">Türkçe Sesli Meal Uygulaması</h2>
<p align="center">
  İnsan, adı anılmaya değer bir şey olana kadar, üzerinden uzun bir süre geçmedi mi?(İnsan:1)
</p>



**Açıklama**

  Bu uygulaması [Android Jetpack](https://developer.android.com/jetpack)  özelliklerini ve **MVVM**  mimarisini örneklemek için hazırlanmış  tek activityden oluşan bir sesli   meal uygulamasıdır. Uygulamanın eğitici olması için  aşamalarla geliştirmeye çalışıyorum.Katılmak isterseniz istediğiniz her türlü özelliği ekleyip değiştirerek projeye katkıda bulunabilirsiniz.
      [Fork ve Pull request nasıl yapılır ? ](https://www.youtube.com/watch?v=dVK1Br0nQk8)

**Çalışma Şekli**

  Uygulama ayet,sure ve çevirmen bilgilerini [Açık Kuran API](https://github.com/ziegfiroyt/acikkuran-api) üzerinden alarak görüntüler.**Room** ile
   okunmuş sureler cihazda sqlLite üzerine depolanır.Sonraki çağrılarda yerel veritabanından okunur. **Mediaplayer** ile surelerin mp3 kayıtları url üzerinden çalınır. 

**Arayüz**

   <p align="center"><img src="https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/Capture.PNG?raw=true" alt="arayüz" /></p>

**Öğrenebilecekleriniz**
 

 - **Volley** ile api üzerinden veri alma.
 - API üzerinden alınan jsonu objeye çevirme(**Gson**)
 - Url üzerinden müzik çalma
 - **Room** kullanarak sqlLite soyutlaması yaparak obje tabanlı yerel veritabanı yaratma.
 
 **Eklemeyi düşündüğüm özellikler**
 
 - LiveData 
 - MVVM modeline uygun package yapısı
 - Müzik çalar Foreground bildirimi
 - Çevirmen seçme
 - Müzik çaları component haline getirip ayırma

**Notlar**

   **MainActivity** üzerinde **YAZAR_ID** değerini değiştirerek çevirmeni seçebilirsiniz.

**Model**

![model0](https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/model0.PNG?raw=true)

   Sadece API kullanarak çalışan versiyonu görmek isteyenler bu commiti inceleyebilir. [version 0.9](https://github.com/sabreys/TurkceMeal/commit/5ae51cfafe672cc4b3ff1d7a055a1695a7388bd1)
      
   ![Model1](https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/model1.PNG?raw=true)
   
 
**MVVM**'e geçiş 

![Model2](https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/model2.PNG?raw=true)

![Model3](https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/model3.PNG?raw=true)
![Model4](https://github.com/sabreys/TurkceMeal/blob/spinner/gorseller/model4.PNG?raw=true)

## 
  **Kaynaklar Ve Teşekkür**
  
<p align="center"><img src="https://raw.githubusercontent.com/ziegfiroyt/acikkuran-api/master/logo.png" alt="Apaçık Kur'an'a andolsun!" /></p>
<h2 align="center">Açık Kuran API</h2>

Açık Kuran API [github](https://github.com/ziegfiroyt/acikkuran-api) [Site](https://acikkuran.com)
     

 - Anlaşılması kolay ve kapsamlı bir Kuranı Kerim API si.Kelime kökü bulma ve farklı sorgu seçenekleri içeriyor. 

"Icon made by [Freepik](https://www.flaticon.com/authors/freepik) from [www.flaticon.com](http://www.flaticon.com/)

 - Ücretsiz olarak iconlar bulabileceğiniz bir kaynak.**Pexels** ve **flaticon** da bu konuda kapsamlı.
