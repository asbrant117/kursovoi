$(document).ready(function() {
  console.log("Loaded")

  // Установливаем обработчик клика на поиск
  $("#searchBtn").click(function() {
    let id = $("#inputId").val()
    console.log("Clicked with id " + id)
    $.getJSON( "/api/country?id=" + id, function(data) {
      console.log(data);
      $("#firstName").val(data.name)
      $("#lastName").val(data.surname)
    });
  });

  // устанавливаем обработчик сохранения
  $("#save").click(function() {
    $.postJSON("api/country",
      {
        id: parseInt($("#inputId").val(), 10),
        name: $("#firstName").val(),
        surname: parseInt($("#lastName").val(), 10)
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