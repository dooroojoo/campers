
        var num = document.getElementById('guest-number').value;
        var minusBtn = document.getElementById('btn-minus');
        var plusBtn = document.getElementById('btn-plus');

        var number = parseInt(num);

        $('#btn-minus').on('click', function(){
            if(number < 2 ){
              document.getElementById('guest-number').setAttribute('value', 1);
            } else {
              number--;
             document.getElementById('guest-number').setAttribute('value', number);
            }            
        }); 
        
        $('#btn-plus').on('click', function(){
            if(number > 5 ){
              document.getElementById('guest-number').setAttribute('value', 6);
            }else {
              number++;
              document.getElementById('guest-number').setAttribute('value', number);
            }              
        });