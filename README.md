# Hava Durumu Uygulaması

Bu proje, kullanıcının girdiği şehre ait güncel hava durumu bilgilerini gösteren basit bir Android uygulamasıdır. Jetpack Compose kullanılarak geliştirilmiştir.

## Özellikler

* Kullanıcının şehir adı girmesini sağlayan bir metin alanı.
* Girilen şehre ait hava durumu bilgilerini getiren bir "Ara" butonu.
* Hava durumu bilgileri yüklenirken bir yükleme göstergesi.
* Hata durumunda kullanıcıya gösterilen bir hata mesajı.
* Başarılı bir şekilde hava durumu bilgileri alındığında aşağıdaki bilgileri gösteren bir kart:
    * Şehir ve ülke adı.
    * Hava durumu ikonu.
    * Hava durumu açıklaması.
    * Güncel sıcaklık (°C).
    * Rüzgar hızı (km/s).
    * Nem oranı (%).

* **UI Kütüphanesi:** Jetpack Compose
* **Mimari:** Basit bir ViewModel yapısı kullanılmıştır.
* **Ağ İletişimi:** Retrofit kütüphanesi kullanılarak bir hava durumu API'sine istekler gönderilmektedir.
* **Veri Serileştirme:** API'den gelen JSON verileri Kotlin data class'larına dönüştürülmektedir.
* **Durum Yönetimi:** Compose'un `mutableStateOf` ve `State` özellikleri ile temel durum yönetimi sağlanmaktadır.
* **Asenkron İşlemler:** Kotlin Coroutines kullanılarak ağ istekleri arka planda gerçekleştirilmektedir.
