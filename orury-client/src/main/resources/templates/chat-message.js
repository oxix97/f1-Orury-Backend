document.addEventListener("DOMContentLoaded", function () {
    const roomId = document.querySelector('input#roomId').value;
    ;
    const loginId = document.querySelector('input#loginId').value;

    console.log(roomId + ", " + loginId);

    const sockJs = new SockJS("/stomp/chat");
    // SockJS를 내부에 들고 있는 stomp를 내어줌
    const stomp = Stomp.over(sockJs);

    // connection이 맺어지면 실행
    stomp.connect({}, function () {
        console.log("STOMP Connection");

        stomp.subscribe("/sub/chat/room/" + roomId, function (chat) {
            loadChatMessages();
        });

        stomp.send('/pub/chat/enter', {}, JSON.stringify({roomId: roomId, loginId: loginId}));
    });

    // 메세지 전송했을 때 처리하는 메서드
    const sendMessage = (e) => {
        const msg = document.querySelector('input#msg');

        console.log(loginId + ":" + msg.value);
        stomp.send('/pub/chat/message', {}, JSON.stringify({roomId: roomId, message: msg.value, loginId: loginId}));
        msg.value = '';
    };

    const sendBtn = document.querySelector('button#btnSend');
    sendBtn.addEventListener('click', sendMessage);

    // 날짜 데이터 formatting
    const formatDateTime = (dateTimeString) => {
        const options = {year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit'};
        return new Date(dateTimeString).toLocaleDateString('ko-KR', options);
    }

    // 채팅 메세지를 불러오기 위한 메서드
    const loadChatMessages = async () => {
        const url = `/api/v1/chat/messages/${roomId}`;

        const response = await axios.get(url);
        console.log(response.data);

        let htmlStr = '';
        for (let chat of response.data) {
            const formattedTime = formatDateTime(chat.createdTime);

            if (chat.sender.loginId == loginId) {
                htmlStr += `
					<div class="d-flex align-items-center alert alert-warning border border-dark">
					  <div class="flex-shrink-0">
					    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
					  </div>
					  <div class="flex-grow-1 ms-3">
					    <h5>${chat.sender.nickname}</h5>
					    <p>${chat.content}</p>
					    <p>${formattedTime}</p>
					  </div>
					</div>
				`;
            } else {
                htmlStr += `
					<div class="d-flex align-items-center alert alert-primary border border-dark">
					  <div class="flex-shrink-0">
					    <img src="https://storage.googleapis.com/itwill-forest-bucket/bd64b031-42c8-4229-a734-b8d25f0fd9f0" alt="..." style="width: 50px; height: 50px;">
					  </div>
					  <div class="flex-grow-1 ms-3">
					    <h5>${chat.sender.nickname}</h5>
					    <p>${chat.content}</p>
					    <p>${formattedTime}</p>
					  </div>
					</div>
				`;
            }
        }

        // 채팅 내역을 불러올 때 가장 최근 메세지가 보이도록 함
        const messageArea = document.querySelector('div#messages');
        messageArea.innerHTML = htmlStr;
        messageArea.scrollTop = messageArea.scrollHeight;
    };

    // 채팅 메세지를 불러옴
    loadChatMessages();
});