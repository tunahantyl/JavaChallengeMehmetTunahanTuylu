# Java Challenge – Kurs Yönetim Sistemi (Spring Boot)

Bu repo basit bir kurs yönetim sistemi. Öğretmen, kurs, öğrenci, sepet ve sipariş mantığı var. Kodlar bilerek sade tutuldu;  çalışır haliyle.

## Neler var?
- Teacher, Course, Student, Cart, Order tabloları (hepsi `BaseEntity`den türetiliyor)
- İlişkiler: Öğretmen→Kurs, Öğrenci→Sepet, Öğrenci→Sipariş, Öğrenci↔Kurs
- Sepet toplamı her ekleme/çıkarma işleminde güncelleniyor
- Sadece aktif ve kontenjanı dolmamış kurslar sepete eklenebiliyor
- REST API ve basit bir frontend (tek HTML + JS)
- H2 in-memory veritabanı (uygulama kapanınca veri sıfırlanır)

## Çalışma mantığı (kısaca)
1) Öğretmen oluşturursunuz.
2) O öğretmen için kurs oluşturursunuz (fiyat ve kontenjan girilir).
3) Öğrenci oluşturursunuz (öğrenciye otomatik boş sepet açılır).
4) Öğrencinin sepetine kurs eklersiniz, toplam fiyat artar.
5) Sipariş verince, sepetteki kurslardan bir sipariş oluşur ve sepet boşaltılır.

## Kurulum
Önkoşullar: Java 17+, Maven

```bash
mvn clean install
```

## Çalıştırma
```bash
mvn spring-boot:run
```
Uygulama: `http://localhost:8080`

## Veritabanı (H2)
H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: (boş)

İsterseniz tablo ve verileri buradan görebilirsiniz. Uygulama kapandığında veriler silinir (in-memory).

## Basit frontend (tek sayfa)
Frontend otomatik servis ediliyor:
- Adres: `http://localhost:8080`
- Sayfada 5 bölüm var: Öğretmen, Kurs, Öğrenci, Sepet, Sipariş
- Formları doldurup butonlara basmanız yeterli. Sonuçlar altta kutularda görünür.

Önerilen akış:
1) Öğretmen oluştur
2) Kurs oluştur (öğretmen ID’yi kullan)
3) Öğrenci oluştur
4) Sepete ekle (öğrenci ID + kurs ID)
5) Sepeti görüntüle (toplam fiyatı gör)
6) Sipariş ver
7) Müşteri siparişlerini listele

## API’leri Postman ile denemek isterseniz
Kısa örnekler (body: JSON):
- Tüm öğretmenler: `GET /api/teachers`
- Öğretmen oluştur: `POST /api/teachers`
```json
{
  "name": "Ahmet",
  "email": "ahmet@example.com",
  "specialization": "Java"
}
```
- Öğretmen için kurs oluştur: `POST /api/courses/teacher/{teacherId}`
```json
{
  "title": "Java 101",
  "description": "Giriş seviyesi",
  "price": 199.99,
  "maxStudents": 20
}
```
- Öğrenci oluştur: `POST /api/students`
```json
{
  "name": "Mehmet",
  "email": "mehmet@student.com"
}
```
- Sepete ekle: `POST /api/cart/{studentId}/add/{courseId}`
- Sepeti görüntüle: `GET /api/cart/{studentId}`
- Sepeti boşalt: `DELETE /api/cart/{studentId}`
- Sipariş ver: `POST /api/orders/{studentId}/place`
- Müşteri siparişleri: `GET /api/orders/customer/{studentId}`


