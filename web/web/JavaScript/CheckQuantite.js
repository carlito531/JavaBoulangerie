function CheckIsNumber(objInput){
   var reg = /^[0-9]*$/;
    
   if (objInput.value === '0')
   {
       alert('Veuillez saisir une quantité supérieur à 0 !');
       objInput.value = '1';
   }
   if(!reg.test(objInput.value)){
      alert('Veuillez ne saisir que des chiffres dans les quantités de produit désirées !');
      objInput.value = '1';
   }
   if ((objInput.value === null) || (objInput.value === ''))
   {
       alert('Veuillez saisir une quantité !');
       objInput.value = '1';
   }
}

function CheckQteIngredient(objInput){
   var reg = /^[1-2]*$/;
   
   if (objInput.value === '0')
   {
       alert('Veuillez saisir une quantité supérieur à 0 !');
       objInput.value = '1';
   }   
   if(!reg.test(objInput.value)){
      alert('Veuillez ne saisir que 1 ou 2 en quantité d\'ingrédient désirée !');
      objInput.value = '1';
   }
   if ((objInput.value === null) || (objInput.value === ''))
   {
       alert('Veuillez saisir une quantité !');
       objInput.value = '1';
   }
}
// Début vérifications Checkbox Max 1 Element
function optSelectedMaxOne(element){
    var cboxMaxOne = document.getElementsByName(element.name);
    for (var i = 0; i < cboxMaxOne.length; i++) {
        if ((cboxMaxOne[i].checked) && (cboxMaxOne[i].value !== element.value)) {
          cboxMaxOne[i].checked = false;          
        }
    }   
}
// Fin vérifications Checkbox Max 1 Element
// Début vérification choix élément principal
function enableOptMainElem(tableToDisplay, tableToHide){
    var tableToEnable = document.getElementById(tableToDisplay);
    var tableToDisable = document.getElementById(tableToHide);
    
    tableToEnable.style.display = "block";
    tableToDisable.style.display = "none";
}
// Fin vérification choix élément principal
// Début vérifications Checkbox Max 2 Elements
function optSelectedMaxTwo(element){
    var myConst = "my_qte_";    
    var cboxMaxTwo = document.getElementsByName(element.name);
    var elementQte = document.getElementById(myConst + element.value);
    var totalQte = 0; 
    
    for(var i = 0; i < cboxMaxTwo.length; i++){
        var iQte = document.getElementById(myConst + cboxMaxTwo[i].value);
                            
        if((cboxMaxTwo[i].checked) && (iQte.value === '2')){
                // alert("On coche que 2");
                if ((element.value === cboxMaxTwo[i].value) && (elementQte.value === '2')){                    
                    uncheckedAllWithoutOne(cboxMaxTwo[i].value, element.name);
                    return;
                }else if ((element.value !== cboxMaxTwo[i].value) && (elementQte.value === '2')){
                    uncheckedAllWithoutOne(element.value, element.name);
                    return;
                }else{
                    element.checked = false;
                }
        }else if ((element.checked) && (totalQte === 2) && (cboxMaxTwo[i].checked)){
                    element.checked = false;
                    var item1 = verifieItemCochePassOne(element.name);
                    var item2 = verifieItemCochePassTwo(item1, element.name);
                    // alert("Items déjà cochés : " + item1 + " " + item2);
                    uncheckedAllWithoutTwo(item1, item2, element.name);
                    return;
                } else if (cboxMaxTwo[i].checked){
                    cboxMaxTwo[i].checked = true;
                    totalQte = totalQte + 1;
            }            
        }
        
        for(var i = 0; i < cboxMaxTwo.length; i++){
            if (cboxMaxTwo[i].checked){
                document.getElementById(myConst + cboxMaxTwo[i].value).readOnly = true;
            }else{
                document.getElementById(myConst + cboxMaxTwo[i].value).readOnly = false;
            }
        }       
}

function uncheckedAllWithoutOne(item, cboxName){
    var myConst = "my_qte_";
    var cboxTypeName = document.getElementsByName(cboxName);
    
    for (var i = 0; i < cboxTypeName.length; i++) {
        if ((cboxTypeName[i].value !== item)) {
          cboxTypeName[i].checked = false;    
          document.getElementById(myConst + cboxTypeName[i].value).readOnly = false;
        }else{
            cboxTypeName[i].checked = true;
            document.getElementById(myConst + cboxTypeName[i].value).readOnly = true;            
        }
    } 
}

function verifieItemCochePassOne(cboxName){
    var cboxTypeName = document.getElementsByName(cboxName);
    
    for (var i = 0; i < cboxTypeName.length; i++) {
        if (cboxTypeName[i].checked){          
            return cboxTypeName[i].value;
        }
    } 
}

function verifieItemCochePassTwo(item, cboxName){
    var cboxTypeName = document.getElementsByName(cboxName);
    
    for (var i = 0; i < cboxTypeName.length; i++) {
        if ((cboxTypeName[i].checked) && (cboxTypeName[i].value !== item)){          
            return cboxTypeName[i].value;
        }
    }
}

function uncheckedAllWithoutTwo(item1, item2, cboxName){
    var myConst = "my_qte_";
    var cboxTypeName = document.getElementsByName(cboxName);
    
    for (var i = 0; i < cboxTypeName.length; i++) {
        if ((cboxTypeName[i].value !== item1) && (cboxTypeName[i].value !== item2)) {
          cboxTypeName[i].checked = false; 
          document.getElementById(myConst + cboxTypeName[i].value).readOnly = false;          
        }else{
            document.getElementById(myConst + cboxTypeName[i].value).readOnly = true;
        }
    } 
}
// Fin vérifications Checkbox Max 2 Elements


function uncheckedRadioButton(){
    var radioMainElement = document.getElementsByName('main_element');
    
    for (var i = 0; i < radioMainElement.length; i++) {
        radioMainElement[i].checked = false;
    }
}
