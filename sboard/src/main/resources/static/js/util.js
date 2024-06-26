// GET //
async function fetchGet(url){
    console.log("fetchGet : "+url);
    try{
       const response = await fetch(url);
        if(!response.ok){
            throw new Error("response not ok");
        }
        const data = await response.json();
        console.log("fetchGet data : "+data);
        return data;

    }catch (err) {
        console.log(err);
    }
}
// POST //
async function fetchPost(url, jsonData){

    console.log("fetchPost...1" +jsonData);

    try{
        console.log("fetchPost...2");
        const response = await fetch(url, {
            method: 'POST',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });
        console.log("fetchPost...3"+JSON.stringify(jsonData));

        if(!response.ok){
            console.log("fetchPost...4");
            return null;
        }

        const data = await response.json();
        console.log("fetchData2...5 : " + data);

        return data;

    }catch (err) {
        console.log(err);
        return null;
    }
}
// PUT
async function fetchPut(url, jsonData){
        console.log("fetchPut...1");
    try{
        const response = await fetch(url, {
            method: 'PUT',
            headers: {"Content-type":"application/json"},
            body: JSON.stringify(jsonData)
        });
        console.log("fetchPut...2");
        if(!response.ok){
            throw new Error('response not ok');
        }
        console.log("fetchPut...3");
        const data = await response.json();
        console.log("data1 : " + data);
        console.log("fetchPut...4");
        return data;

    }catch (err) {
        console.log(err);
        return null;
    }
}
// 게시글 + 파일 수정
async function fetchPutForFile(url, formData) {
    console.log("fetchPutForFile...1");
    try {
        const response = await fetch(url, {
            method: 'PUT',
            body: formData
        });
        console.log("fetchPutForFile...2");
        if (!response.ok) {
            throw new Error('response not ok');
        }
        console.log("fetchPutForFile...3");
        const data = await response.json();
        console.log("data1 : " + data);
        console.log("fetchPutForFile...4");
        return data;
    } catch (err) {
        console.log(err)
    }
}

async function fetchDelete(url) {
    try {
        const response = await fetch(url, {
            method: 'DELETE'
        });
        console.log("fetchDelete : " + response);
        if (!response.ok) {
            throw new Error("response not ok");
        }
        const data = await response.json();
        console.log("fetchDelete data : " + data);

        return data;

    } catch (err) {
        console.log(err);
    }
}

function alertModal(message){
    const modal = document.getElementById('alertModal');
    modal.getElementsByClassName('modal-body')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show();
}
function confirmModal(message) {
    const modal = document.getElementById('confirmModal');
    modal.getElementsByClassName('modal-body')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show(); // 모달 열기

    // 결과값 반환
    return new Promise(resolve => {
        // 확인 버튼 클릭 시
        document.getElementById('btnOk').onclick = function () {
            resultModal.hide(); // 모달 닫기
            resolve(true); // 확인 결과값 반환
        };

        // 취소 버튼 클릭 시
        document.getElementById('btnCancel').onclick = function () {
            resultModal.hide(); // 모달 닫기
            resolve(false); // 취소 결과값 반환
        };
    });
}
function passFormModal(message, uid) {
    const rePass  = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{5,16}$/;
    let isPassOk  = false;
    const modal = document.getElementById('formModal');
    const passCancel = document.getElementById('passCancel');
    const passSubmit = document.getElementById('passSubmit');
    const resultCheckPass = document.getElementsByClassName('resultCheckPass')[0];
    modal.getElementsByClassName('modal-title')[0].innerText = message;
    const resultModal = new bootstrap.Modal(modal);
    resultModal.show(); // 모달 열기

    // Promise를 사용하여 모달의 상태를 반환
    return new Promise((resolve, reject) => {
        // 폼 제출 이벤트를 감지하여 처리
        const passChangeForm = document.getElementById('passChangeForm');
        passChangeForm.pass1.addEventListener('focusout', function(e) {
            e.preventDefault(); // 기본 폼 제출 동작 방지

            // 사용자가 입력한 값을 가져와서 유효성 검사
            const pass1 = passChangeForm.pass1.value;

                if (!pass1.match(rePass)) {
                    resultCheckPass.classList.add('invalid-feedback');
                    resultCheckPass.innerText = '5~16자의 영문 대/소문자, 숫자, 특수문자를 사용해 주세요.';
                    isPassOk =false;
                } else {
                    resultCheckPass.innerText = "";
                    resultCheckPass.classList.remove('invalid-feedback');
                    isPassOk = true;
                }
        });

        passSubmit.addEventListener('click', async function (e) {
            const pass1 = passChangeForm.pass1.value;
            const pass2 = passChangeForm.pass2.value;
            if (pass1 === pass2 && isPassOk === true) {
                const jsonData = {
                    "uid": uid,
                    "pass": pass1
                };
                const data = await fetchPut('/sboard/user/findPasswordChange', jsonData);
                if (data != null) {
                    resolve(true);
                    // 모달 닫기
                    resultModal.hide();
                }else {
                    resolve(false);
                    // 모달 닫기
                    resultModal.hide();
                }


            } else {
                resultCheckPass.classList.add('invalid-feedback');
                resultCheckPass.innerText = '비밀번호가 일치하지 않습니다.';
            }
        });
        passCancel.addEventListener('click', function(e) {
            e.preventDefault(); // 기본 폼 제출 동작 방지

            resolve(false);
            // 모달 닫기
            resultModal.hide();
        });

    });
}
////////댓글 수정 시 수정모드 해제
function noModifyMode(comment){

    const btnModify = comment.getElementsByClassName('btnModify')[0];
    const btnRemove = comment.getElementsByClassName('btnRemove')[0];
    const textarea = comment.getElementsByTagName('textarea')[0];

    // 아이콘
    const trashIcon = document.createElement('i');
    trashIcon.classList.add('bi', 'bi-trash3-fill');
    btnRemove.parentNode.insertBefore(trashIcon, btnRemove);

    const penIcon = document.createElement('i');
    penIcon.classList.add('bi', 'bi-pencil-fill');
    btnModify.parentNode.insertBefore(penIcon, btnModify);

    textarea.readOnly = true;
    textarea.style.outline = "none"
    btnModify.dataset.mode = 'modify';
    btnRemove.textContent = ' 삭제';
    btnRemove.dataset.mode = 'remove';
    btnModify.textContent = ' 수정';
}

function postcode(){
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('inputZip').value = data.zonecode;
            document.getElementById("inputAddr1").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("inputAddr2").focus();
        }
    }).open();

}