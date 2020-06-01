

/* html файл редактирования edit.scala.html */


$(document).ready(function() {
  console.log("Loaded");

  // Установливаем обработчик клика на поиск
  $("#searchBtn").click(function() {
    let id = $("#inputId").val()
    console.log("Clicked with id " + id);

    // делаем запрос
    $.getJSON("/api/boardgame?id=" + id, /* что сделаем, при успешном ответе -> */ function(data) {
      console.log(data);
      /* data.name , name должен совпадать с Json именем поля profileWrite в JsonController */
      /* #firstName , firstName должен совпадать с id в html  ищи ctrl+f id="firstName" */
      $("#boardGameName").val(data.name)
      $("#genreName").val(data.genre)
      $("#countryName").val(data.country)
      $("#ratingValue").val(data.rating)
    });
  });

  // устанавливаем обработчик сохранения
  $("#save").click(function() {
    $.postJSON("api/country",
      {
        id: parseInt($("#inputId").val(), 10),
        name: $("#firstName").val(),
        surname: parseInt($("#population").val(), 10)
      },
      function(response) {
        alert(response.result);
      },
      function(xhr, status, err) {
        alert("Ошибка сохранения");
      }
    );
  });
});