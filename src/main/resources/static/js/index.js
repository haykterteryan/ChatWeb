var $chat = $('#chat');
var notifContainer = $('#notifContainer');
var $txtMessage = $('#txtMessage');
var friendId = $('#friend').val();

$txtMessage.on('keypress', function (e) {
    if (e.which === 13) {
        sendMessage();
    }
});

var socket = new SockJS('/ws');
var stompClient = Stomp.over(socket);

stompClient.connect({}, function (frame) {
    stompClient.subscribe('/user/message', function (data) {
        var body = JSON.parse(data.body);
        var message = body.message;
        var firstName = body.firstName;
        var lastName = body.lastName;
        var receiverId = body.receiverId;
        var senderId = body.senderId;
        console.log(receiverId ,  friendId);
        if(senderId == friendId) {
            var $divmsgrm = $('<div/>').addClass('msg left-msg');
            var $divmsgbubble = $('<div/>').addClass('msg-bubble');
            var $divmsginfo = $('<div/>').addClass('msg-info');
            var $divmsginfotime = $('<div/>').addClass('msg-info-time');
            var $divmsgtext = $('<div/>').addClass('msg-text');



            $chat.append($divmsgrm
                .append($divmsgbubble.append($divmsginfo.append($divmsginfotime))
                    .append($divmsgtext.append(message))));

            markAsReaded(senderId);
        }else {
            var $divContainer = $('<div/>').addClass('container');
            var $divListItem = $('<div/>').addClass('list-item');
            var $divItemContent= $('<div/>').addClass('item-content');

            notifContainer.append($divContainer
                .append($divListItem.append($divItemContent.append("You have message from " + firstName +" "+ lastName))));
        }
    });
});

function markAsReaded(persionId) {
    $.ajax('/markAsReaded', {
        type: 'POST',
        data: persionId.toString(),
        contentType: "text/plain",
        xhrFields: {
            withCredentials: true
        }

    })
}



function sendMessage() {
    var message = $txtMessage.val();
    if ($txtMessage.val() === "") {
        alert("Please type message!");
        return false;
    }
    send(message,friendId);
}

function send(message,receiverId) {
    var mess = {
        message:message,
        firstName: "",
        lastName: "",
        receiverId: receiverId,
        senderId: 0
    };

    $.ajax('/api/send', {
        type: 'POST',
        data: JSON.stringify(mess),
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },

        success: function (response) {
            var $divmsgrm = $('<div/>').addClass('msg right-msg');
            var $divmsgbubble = $('<div/>').addClass('msg-bubble');
            var $divmsginfo = $('<div/>').addClass('msg-info');
            var $divmsginfotime = $('<div/>').addClass('msg-info-time');
            var $divmsgtext = $('<div/>').addClass('msg-text');


            $chat.append($divmsgrm
                .append($divmsgbubble.append($divmsginfo.append($divmsginfotime))
                    .append($divmsgtext.append(mess.message))));

            $txtMessage.val('');
        }
    });
}

function friendRequest(userId, isAccept) {


    var request = {
            userId: userId,
            isAccept: isAccept
        };

    return $.ajax('/request',{
        type: "POST",
        data: JSON.stringify(request),
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            $("#notification").hide();
        }
    });
}

function sendFriendRequest(userId) {
    var request = {
        userId: userId
    };

    return $.ajax('/friendrequest',{
        type: "POST",
        data: JSON.stringify(request),
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        success: function (response) {
            $("#result").hide();
        }
    });
}