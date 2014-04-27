function clearTextBox(evt)
{
    var myTextBox = document.getElementById('txtbox_identifiant');
    
    if(myTextBox.value.length === 0 && evt.type === "blur") 
    { 
        myTextBox.value = "Identifiant...";
    }
    if (myTextBox.value === "Identifiant..." && evt.type === "focus") 
    { 
        myTextBox.value = "";
    }
}