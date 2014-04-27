/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun.model;

/**
 * Obtient ou définit un client
 *
 * @author INF-PORT-CR2
 */
public class Client {

    private long id;
    private String login;
    private String motDePasse;
    private String nom;
    private String prenom;
    private String adresse;
    private int codePostal;
    private String ville;
    private String mail;
    private boolean cuisinier;
    private boolean boulanger;

    public Client(String login, String motDePasse, String nom, String prenom, String adresse, int codePostal, String ville, String mail, boolean cuisinier, boolean boulanger) {
        this.login = login;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.mail = mail;
        this.cuisinier = cuisinier;
        this.boulanger = boulanger;
    }

    public Client(Long id, String login, String motDePasse, String nom, String prenom, String adresse, int codePostal, String ville, String mail, boolean cuisinier, boolean boulanger) {

        this.id = id;
        this.login = login;
        this.motDePasse = motDePasse;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.codePostal = codePostal;
        this.ville = ville;
        this.mail = mail;
        this.cuisinier = cuisinier;
        this.boulanger = boulanger;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isCuisinier() {
        return cuisinier;
    }

    public void setCuisinier(boolean cuisinier) {
        this.cuisinier = cuisinier;
    }

    public boolean isBoulanger() {
        return boulanger;
    }

    public void setBoulanger(boolean boulanger) {
        this.boulanger = boulanger;
    }

}
