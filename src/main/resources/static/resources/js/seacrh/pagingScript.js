// 클릭한 페이지 이동 이벤트
var aa = jQuery.noConflict();
    aa(function(){    
    	aa(".find-paging").click(function(){
    		let area = aa(".find-area").val();
    		let dateIn = aa(".find-in-date").val();
    		let dateOut = aa(".find-out-date").val();
    		let guest = aa(".find-guest").val();
    		let name = aa("#find-name").val();
    		let typeObj = aa("#search-type-val").val();
    		let faciObj= aa("#find-faci-val").val();
    		let floorObj = aa("#find-floor-val").val();
    		
            location.href='/search/find?page='+aa(this).text()+'&area='+area+'&type='+typeObj+'&daterange='+dateIn
            		+'+~+'+dateOut+'&quantity='+guest+'&name='+name+'&facility='+faciObj+'&floor='+floorObj; 
        });
    });
    

// 첫페이지로 이동
  var cc = jQuery.noConflict();
    cc(function(){    
    	cc(".first-paging").click(function(){
    		let area = cc(".find-area").val();
    		let dateIn = cc(".find-in-date").val();
    		let dateOut = cc(".find-out-date").val();
    		let guest = cc(".find-guest").val();
    		let name = cc("#find-name").val();
    		let typeObj = cc("#search-type-val").val();
    		let faciObj= cc("#find-faci-val").val();
    		let floorObj = cc("#find-floor-val").val();
    		
            location.href='/search/find?page=1'+'&area='+area+'&type='+typeObj+'&daterange='+dateIn
            		+'+~+'+dateOut+'&quantity='+guest+'&name='+name+'&facility='+faciObj+'&floor='+floorObj; 
        });
    });
    
    