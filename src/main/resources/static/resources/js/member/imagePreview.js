/* input type file 요소들 */
const fileElements = document.querySelectorAll("[type=file]");
/* div image_area 요소들 */
const imageArea = document.querySelectorAll(".image_area");
/* input type file 요소에 change 이벤트 발생 시 (파일 첨부 발생) */
fileElements.forEach(item => item.addEventListener('change', preview));

function preview(){
	let index = Array.from(fileElements).indexOf(this);   //input type="file" 이게 this
	if(this.files && this.files[0]) {
		let reader = new FileReader();
		reader.readAsDataURL(this.files[0]);
		reader.onload = function() {
			imageArea[index].innerHTML = "<img class='profileImg after' src='" + reader.result + "'>";
		}
	}
}