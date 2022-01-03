$(function() {

	var toDay = new Date();
	
    $('input[name="daterange"]').daterangepicker({
      "locale": {
                      "format": "YYYY-MM-DD",
                      "separator": " ~ ",
                      "applyLabel": "확인",
                      "cancelLabel": "취소",
                      "daysOfWeek": ["월", "화", "수", "목", "금", "토", "일"],
                      "monthNames": ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
                    },
                  "drops": "down",
                  maxDate: 0,
                  minDate: 0,
                  startDate:toDay.setDate(toDay.getDate()+1),
                  endDate: toDay
  });

  });