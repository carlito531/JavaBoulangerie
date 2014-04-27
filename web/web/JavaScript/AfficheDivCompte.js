function affiche(id) 
{
    document.getElementById(id).style.display = "block"; 
}
function cache(e, id) {
    var 
    relTarg = e.relatedTarget || e.toElement;
    if (!isChildOf(relTarg, document.getElementById('conteneur'))) 
    {
        document.getElementById(id).style.display = "none";
    } 
 }
 function isChildOf(child, par) 
 {
     while (child !== document) 
     {
         if (child === par) {
             return true;
         }
         child = child.parentNode;
     } 
     return false; 
 }
 
 function cache_panier(e, id) {
    var 
    relTarg = e.relatedTarget || e.toElement;
    if (!isChildOf(relTarg, document.getElementById('conteneur_panier'))) 
    {
        document.getElementById(id).style.display = "none";
    } 
 }


