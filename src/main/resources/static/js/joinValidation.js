let wasCheckedId = false;
// const now = new Date(convertDate(new Date()));
const now = convertDate(new Date());

// 이메일 형식

const emailRegex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;


// 영어 소문자 + 대문자 + 특수문자 조합, 길이 : 8~12
const passwordRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,12}$/;


// 영어 소문자 또는 영어소문자+숫자 조합, 길이 : 4~10
const idRegex = /^[0-9a-z]{4,10}$/;


const nameRegex = /^[가-힣]{2,4}|[a-zA-Z]{2,10}\s[a-zA-Z]{2,10}$/;


function checkId() {

    id = $("#id").val();

    if(id.length == 0) {
        alert("아이디를 입력해주세요");
        return;
    }

    if(!idRegex.test(id)) {
        alert("아이디를 조건에 맞게 작성해주세요");
        return;
    }

    console.log("id : " + id);

    $.ajax({
        type: "get",
        url: "/join/checkDuplicationId",
        data: "id="+id,
        dataType: "text",
        success: function(data) {
            console.log("data : " + data);
            if(data == "dup"){
                alert("중복된 아이디입니다.");
                wasCheckedId = false;
            }
            else {
                alert("사용가능한 아이디입니다.");
                wasCheckedId = true;
            }
        },
        error(request, status, error) {
            console.log("request : " + request);
            console.log("status : " + status);
            console.log("error : " + error);
        }
    });

};

function checkPassword(password, repeatedPassword) {

    if(password.length == 0) {
        alert("비밀번호를 입력해주세요");
        return false;
    }

    if(repeatedPassword.length == 0) {
        alert("비밀번호 확인을 입력해주세요.");
        return false;
    }

    if(password != repeatedPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        return false;
    }

    if(!passwordRegex.test(password)) {
        alert("비밀번호를 조건에 맞게 입력해주세요.");
        return false;
    }

    return true;
}

function checkName(name){


    if(name.length == 0) {
        alert("이름을 입력해주세요.");
        return false;
    }

    if(!nameRegex.test(name)){
        alert("이름을 정확히 입력해주세요.")
        return false;
    }

    return true;

}

function checkEmail(email) {


    if(email.length == 0) {
        alert("이메일을 입력해주세요");
        return false;
    }

    if(!emailRegex.test(email)) {
        alert("이메일을 형식에 맞게 입력해주세요.");
        return false;
    }

    return true;

}



function checkBirthDate(birthDate) {


    if(birthDate.toString() == "Invalid Date") {
        alert("생일을 입력해주세요.");
        return false;
    }


    if(birthDate.getTime() >= now.getTime()) {
        alert("생일이 오늘보다 늦을 수 없습니다.");
        return false;
    }

    return true;


}

function convertDate(date) {

    const year = date.getFullYear();

    let month = date.getMonth();
    month = month < 10 ? "0" + month : month;

    let day = date.getDate();
    day = day < 10 ? "0" + day : day;

    return new Date(year, month, day);

}

function goPopup(){
    var pop = window.open("/juso-popup","pop","width=570,height=420, scrollbars=yes, resizable=yes");
}

function jusoCallBack(roadAddrPart1,addrDetail,roadAddrPart2,zipNo){

    $("#postNumber").val(zipNo);
    $("#roadAddress").val(roadAddrPart1);
    $("#detailAddress").val(addrDetail+roadAddrPart2);
}