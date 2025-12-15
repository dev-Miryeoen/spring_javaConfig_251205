console.log("in!");

document.getElementById('trigger').addEventListener('click',()=>{
    document.getElementById('file').click();
});

// 파일 업로드 규칙
// 1. 20MB 파일 사이즈 이상은 금지
// 2. 실행파일 금지(exe, sh, msi, dll, bat, jar)
const regExp = new RegExp("\.(exe|sh|msi|dll|bat|jar)$");
const maxSize = 1024 * 1024 * 20;

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxSize){
        return 0;
    }else if(fileSize <= 0){
        return 0;
    }else{
        return 1;
    }
}

document.addEventListener('change', (e)=>{
    if(e.target.id == 'file'){ // input file 객체에 변화가 생겼다면...
        // 파일 객체를 가져올 때 files property 사용
        const fileObj = document.getElementById('file').files;
        console.log(fileObj);

        const div = document.getElementById('fileZone');
        div.innerHTML = ''; // 지난 파일은 삭제
        // 내가 등록한 파일의 목록을 fileZone 영역에 추가 => valid의 정보도 같이 표시

        // 한번 disabled 된 버튼은 혼자 false로 돌아갈 수 없음 (원상복구)
        document.getElementById('regBtn').disabled = false;

        let isOk = 1; // 각 파일마다 valid 검증을 통과했는지의 여부
        // 여러 파일을 등록가능 => 모두다 valid 통과해야 등록 가능
        // 모든 검증 결과가 1이여야 register 버튼을 활성화 => 누적곱을 통해 검증

        let ul = `<ul class="list-group list-group-flush">`;
            // 개별 파일에 대한 검증 / 결과표시
            for(let file of fileObj){
                let validResult = fileValidation(file.name, file.size);
                isOk *= validResult;
                ul += `<li class="list-group-item">`;
                ul += `<div class="mb-3">`;
                ul += `${validResult ? '<div class="fw-bold text-success">업로드 가능</div>' : '<div class="fw-bold text-danger">업로드불가능</div>'}`;
                ul += `${file.name} `;
                ul += `<span class="badge text-bg-${validResult? 'success':'danger'}">${file.size}Bytes</span>`;
                ul += `</div></li>`;
            }
        ul += `</ul>`;
        div.innerHTML = ul;
        if(isOk == 0) {
            // 파일 중 하나라도 검증을 통과하지못한다면 등록버튼 비활성화
            document.getElementById('regBtn').disabled = true;
        }
    }
})