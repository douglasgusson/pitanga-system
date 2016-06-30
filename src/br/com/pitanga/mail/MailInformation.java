package br.com.pitanga.mail;

import java.util.List;
import java.util.Map;
 
public class MailInformation {
 
     public static final String TYPE_TEXT_PLAIN = "text/plain";
     public static final String TYPE_TEXT_HTML = "text/html";
 
     private String smtpHostMail = null;
     private String smtpPortMail = null;
     private String smtpAuth = null;
     private String smtpStarttls = null;
     private String fromNameMail = null;
     private String userMail = null;
     private String passMail = null;
     private String subjectMail = null;
     private String bodyMail = null;
     private Map toMailUsers = null;
     private List fileMails = null;
     private String chartSetMail = null;
     private String typeTextMail = null;
 
     public String getSmtpHostMail() {
         return smtpHostMail;
     }
 
     public void setSmtpHostMail(String smtpHostMail) {
         this.smtpHostMail = smtpHostMail;
     }
 
     public String getSmtpPortMail() {
         return smtpPortMail;
     }
 
    public void setSmtpPortMail(String smtpPortMail) {
        this.smtpPortMail = smtpPortMail;
    }
 
    public String getSmtpAuth() {
        return smtpAuth;
     }
 
     public void setSmtpAuth(String smtpAuth) {
         this.smtpAuth = smtpAuth;
     }
 
     public String getSmtpStarttls() {
         return smtpStarttls;
     }
 
     public void setSmtpStarttls(String smtpStarttls) {
         this.smtpStarttls = smtpStarttls;
     }
 
     public String getFromNameMail() {
        return fromNameMail;
     }
 
     public void setFromNameMail(String fromNameMail) {
         this.fromNameMail = fromNameMail;
     }
 
     public String getUserMail() {
         return userMail;
     }
 
     public void setUserMail(String userMail) {
         this.userMail = userMail;
     }
 
     public String getPassMail() {
         return passMail;
     }
 
     public void setPassMail(String passMail) {
         this.passMail = passMail;
     }
 
     public String getSubjectMail() {
        return subjectMail;
     }
 
     public void setSubjectMail(String subjectMail) {
         this.subjectMail = subjectMail;
     }
 
     public String getBodyMail() {
         return bodyMail;
     }
 
     public void setBodyMail(String bodyMail) {
        this.bodyMail = bodyMail;
     }
 
     public List getFileMails() {
         return fileMails;
     }
 
     public void setFileMails(List fileMails) {
         this.fileMails = fileMails;
     }
 
     public Map getToMailUsers() {
         return toMailUsers;
     }
 
     public void setToMailUsers(Map toMailUsers) {
         this.toMailUsers = toMailUsers;
     }
 
     public String getChartSetMail() {
         return chartSetMail;
     }
 
     public void setChartSetMail(String chartSetMail) {
         this.chartSetMail = chartSetMail;
     }
 
     public String getTypeTextMail() {
         return typeTextMail;
     }
 
    public void setTypeTextMail(String typeTextMail) {
        this.typeTextMail = typeTextMail;
     }
 
}