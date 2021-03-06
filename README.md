# О приложении

Приложение «Вонь в округе!» позволит Вам отправить жалобу на загрязнение воздуха и неприятные запахи в компетентные органы государственной власти по городу Москва и Московской области.

Выберите неприятный запах, адресата и укажите местоположение на карте, «Вонь в округе!» создаст для Вас электронное письмо.

![Экраны приложения](/docs/app_screens.png)

# Инструкция по сборке

Необходимо предоставить следующие ключи API:
- [API Портала открытых данных](https://apidata.mos.ru) - добавьте ключ **"MosApiKey"** в файл **"local.properties"**
- [Google Maps SDK](https://console.cloud.google.com/apis) - добавьте ключ в ресурс **"values/google_maps_api.xml"**
- [Firebase Crashlytics](https://console.firebase.google.com/) - приложите файл **"app/google-services.json"**

# Используемые технологии
- ViewModel & LiveData
- Volley
- Google Maps
- Espresso