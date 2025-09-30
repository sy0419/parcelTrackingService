// 1. 요소 선택
const input = document.getElementById('invoiceNumber');
const button = document.getElementById('searchButton');
const result = document.getElementById('resultArea');

// 2. 버튼 클릭 이벤트
button.addEventListener('click', function () {
  const invoiceNumber = input.value;

  // ✅ 3. fetch API 호출
  fetch(`http://localhost:8080/api/delivery/${invoiceNumber}`)
    .then(response => {
      if (!response.ok) {
        throw new Error('송장번호 조회 실패');
      }
      return response.json(); // JSON 응답 받기
    })
    .then(data => {
      // ✅ 4. 결과 출력
      result.innerHTML = `
        <p><strong>송장번호:</strong> ${data.invoiceNumber}</p>
        <p><strong>배송 상태:</strong> ${data.status}</p>
        <p><strong>도착 예정일:</strong> ${data.estimatedArrival}</p>
      `;
    })
    .catch(error => {
      // 에러 발생 시 메시지 출력
      result.textContent = '조회 중 오류가 발생했습니다: ' + error.message;
    });
});