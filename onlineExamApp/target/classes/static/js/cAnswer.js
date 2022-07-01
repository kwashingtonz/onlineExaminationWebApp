function getCorrectAnswer(){
		
		const a = document.getElementById("firstAnswer").value;
		const b = document.getElementById("secondAnswer").value;
		const c = document.getElementById("thirdAnswer").value;
    	const d = document.getElementById("fourthAnswer").value;
		
		
		if(document.getElementById("radio1").checked){
			
			document.getElementById("correctAnswer").value = a;
		}
		
		if(document.getElementById("radio2").checked){
							
			document.getElementById("correctAnswer").value = b; 
		}
		
		if(document.getElementById("radio3").checked){
			
			document.getElementById("correctAnswer").value = c;
		}
		
		if(document.getElementById("radio4").checked){
			
			document.getElementById("correctAnswer").value = d;
		}
		
		return true;
}