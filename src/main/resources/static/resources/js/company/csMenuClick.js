function menuClick(num){

    var menu1 = document.getElementById('cs-cancle');
    var menu2 = document.getElementById('cs-pay');
    var menu3 = document.getElementById('cs-booking');
    var menu4 = document.getElementById('cs-service');


    if (num == "1") {
      menu1.style.display = "block";
      menu2.style.display = "none";
      menu3.style.display = "none";
      menu4.style.display = "none";

    }
    else if (num == "2") {
      menu1.style.display = "none";
      menu2.style.display = "block";
      menu3.style.display = "none";
      menu4.style.display = "none";
    }
    else if (num == "3"){
      menu1.style.display = "none";
      menu2.style.display = "none";
      menu3.style.display = "block";
      menu4.style.display = "none";
    }
    else{
      menu1.style.display = "none";
      menu2.style.display = "none";
      menu3.style.display = "none";
      menu4.style.display = "block";
    }
  };