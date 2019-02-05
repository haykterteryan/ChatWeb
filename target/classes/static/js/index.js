let $chat = $('#chat');
let $txtMessage = $('#txtMessage');

$txtMessage.on('keypress', function (e) {
    if (e.which === 13) {
        sendMessage();
    }
});

let socket = new SockJS('/ws');
let stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe('/user/message', function (data) {
        let message = data.body;

        let $divmsgrm = $('<div/>').addClass('msg left-msg');
        let $divmsgbubble = $('<div/>').addClass('msg-bubble');
        let $divmsginfo = $('<div/>').addClass('msg-info');
        let $divmsginfotime = $('<div/>').addClass('msg-info-time');
        let $divmsgtext = $('<div/>').addClass('msg-text');

        $chat.append($divmsgrm
                .append($divmsgbubble.append($divmsginfo.append($divmsginfotime))
                    .append($divmsgtext.append(message))));
    });
});

function sendMessage(personId) {
    let message = $txtMessage.val();
    send(message,personId);
}

function send(message,personId) {
    $.ajax('/api/send', {
        type: 'POST',
        data: {
            message: message,
            personId: personId
        },
        contentType: 'text/plain',
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            let $divmsgrm = $('<div/>').addClass('msg right-msg');
            let $divmsgbubble = $('<div/>').addClass('msg-bubble');
            let $divmsginfo = $('<div/>').addClass('msg-info');
            let $divmsginfotime = $('<div/>').addClass('msg-info-time');
            let $divmsgtext = $('<div/>').addClass('msg-text');
            console.log('3');

            $chat.append($divmsgrm
                .append($divmsgbubble.append($divmsginfo.append($divmsginfotime))
                    .append($divmsgtext.append(message))));

            $txtMessage.val('');
        }
    });
}


