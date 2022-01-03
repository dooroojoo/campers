var menuBtn = document.getElementsByClassName("qna-menu");

    function handleClick(event) {

      if (event.target.classList[1] === "clicked") {
        event.target.classList.remove("clicked");
      } else {
        for (var i = 0; i < menuBtn.length; i++) {
          menuBtn[i].classList.remove("clicked");
        }

        event.target.classList.add("clicked");
      }
    }

    function init() {
      for (var i = 0; i < menuBtn.length; i++) {
        menuBtn[i].addEventListener("click", handleClick);
      }
    }

    init();
