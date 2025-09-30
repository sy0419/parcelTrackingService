// 1. 요소 선택  # Select HTML elements
const input = document.getElementById('invoiceNumber');
const button = document.getElementById('searchButton');
const result = document.getElementById('resultArea');

// 2. 버튼 클릭 이벤트 등록  # Register button click event
button.addEventListener('click', function () {
  const invoiceNumber = input.value; // 입력창의 값을 가져옴  # Get value from input box

  // 3. 결과 영역에 텍스트 출력  # Display result text in result area
  result.textContent = `입력한 송장번호: ${invoiceNumber}`;
});