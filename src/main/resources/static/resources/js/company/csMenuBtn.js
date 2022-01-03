$(function(){
    $("dd").hide();

    $("dt").click(function(){
        if($(this).next().css("display")=="none"){
                    $(this).next().slideDown("fast");
                    $('img', this).attr("src", '/resources/images/company/qna2.png');
        }else{
            $(this).next().slideUp("fast");
            $('img', this).attr("src", '/resources/images/company/qna1.png');
        }
    });
});