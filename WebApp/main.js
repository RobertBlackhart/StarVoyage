chosenRoles = {};

window.onload = function() 
{
    cast.receiver.logger.setLevelValue(0);
    window.castReceiverManager = cast.receiver.CastReceiverManager.getInstance();
    console.log('Starting Receiver Manager');

    // create a CastMessageBus to handle messages for a custom namespace
    window.messageBus = window.castReceiverManager.getCastMessageBus('urn:x-cast:com.robertmcdermot.starvoyager');
      
    // handler for the 'ready' event
    castReceiverManager.onReady = function(event) 
    {
        console.log('Received Ready event: ' + JSON.stringify(event.data));
        window.castReceiverManager.setApplicationState("Application status is ready...");
    };
      
    // handler for 'senderconnected' event
    castReceiverManager.onSenderConnected = function(event) 
    {
        console.log('Received Sender Connected event: ' + event.data);
        console.log(window.castReceiverManager.getSender(event.data).userAgent);

        //send the list of taken role choices
        for(senderId in window.castReceiverManager.getSenders())
        {
        	window.messageBus.send(senderId, chosenRoles);
        }
    };
      
    // handler for 'senderdisconnected' event
    castReceiverManager.onSenderDisconnected = function(event) 
    {
        console.log('Received Sender Disconnected event: ' + event.data);
        if (window.castReceiverManager.getSenders().length == 0) 
        {
            window.close();
        }
    };
      
    // handler for 'systemvolumechanged' event
    castReceiverManager.onSystemVolumeChanged = function(event) 
    {
        console.log('Received System Volume Changed event: ' + event.data['level'] + ' ' +
            event.data['muted']);
    };

    // handler for the CastMessageBus message event
    window.messageBus.onMessage = function(event)
    {
        console.log('Message [' + event.senderId + ']: ' + event.data);
        // display the message from the sender
        displayText(event.data);

        //reserve this role so other players can't pick it
        chosenRoles[JSON.parse(event.data)['roleChoice']] = event.senderId;
        // inform all senders on the CastMessageBus of the incoming message event
        // sender message listener will be invoked
        for(senderId in window.castReceiverManager.getSenders())
        {
        	window.messageBus.send(senderId, event.data);
        }
    }

    // initialize the CastReceiverManager with an application status message
    window.castReceiverManager.start({statusText: "Application is starting"});
	console.log('Receiver Manager started');
};
    
// utility function to display the text message in the input field
function displayText(text) 
{
    console.log(text);
    var json = JSON.parse(text);
    role = json['roleChoice'];
    console.log('choicetext: ' + role);
    document.getElementById("message").innerHTML = role+" was chosen";
    //window.castReceiverManager.setApplicationState(text);
};