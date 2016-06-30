package br.com.pitanga.mail;

import br.com.pitanga.domain.Venda;
import br.com.pitanga.util.StringUtils;
import java.awt.HeadlessException;
import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 *
 * @author Douglas Gusson
 */
public class MailSender {

    private static final String TRANSPORT_PROTOCOL = "smtp";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_SOCKET_FACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    private static final String SMTP_SOCKETFACTORY_PORT = "465";
    private static final String SMTP_AUTH = "true";
    private static final String SMTP_PORT = "465";

    //mail.smtp.socketFactory.class", ""

    public void enviarVenda(Venda venda) {

        try {
            Properties props = new Properties();
            props.put("mail.transport.protocol", TRANSPORT_PROTOCOL);
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.socketFactory.class", SMTP_SOCKET_FACTORY_CLASS);
            props.put("mail.smtp.socketFactory.port", SMTP_SOCKETFACTORY_PORT);
            props.put("mail.smtp.auth", SMTP_AUTH);
            props.put("mail.smtp.port", SMTP_PORT);

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "email@gmail.com", "senha");
                        }
                    });

            Message message = new MimeMessage(session);
            Address from = new InternetAddress("email@gmail.com");
            Address to = new InternetAddress(venda.getCliente().getEmail());
            message.setFrom(from);
            message.addRecipient(RecipientType.TO, to);

            message.setSentDate(new Date());
            message.setSubject("Venda nº " + StringUtils.inteiroOitoDig(venda.getIdVenda()));
            // conteudo html que sera atribuido a mensagem
            String htmlMessage
                    = "<br/><br/>Segue anexo arquivo PDF referente a venda nº "
                    + StringUtils.inteiroOitoDig(venda.getIdVenda()) + ".\n"
                    + "<br/><br/>\n"
                    + "Este e-mail foi enviado automaticamente pelo sistema da empresa.\n"
                    + "<br/><br/>\n"
                    + "Powered by Ditador Sistemas"
                    + "<a href=\"mailto:email@gmail.com\" "
                    + "target=\"_blank\" class=headline style=\"DISPLAY: block; "
                    + "FONT-SIZE: 11px; COLOR: #333; FONT-FAMILY:arial;\">"
                    + "ditadorsistemas@gmail.com</a>";

            //criando a Multipart
            Multipart multipart = new MimeMultipart();
            //criando a primeira parte da mensagem
            MimeBodyPart attachment0 = new MimeBodyPart();
            //configurando o htmlMessage com o mime type
            attachment0.setContent(htmlMessage, "text/html; charset=UTF-8");
            //adicionando na multipart
            multipart.addBodyPart(attachment0);

            String arquivo = venda.getCaminhoArquivoPDF();
            File file = new File(arquivo);
            //criando a segunda parte da mensagem
            MimeBodyPart attachment1 = new MimeBodyPart();
            //configurando o DataHandler para o arquivo desejado
            //a leitura dos bytes, descoberta e configuracao do tipo
            //do arquivo serão resolvidos pelo JAF (DataHandler e FileDataSource)
            attachment1.setDataHandler(new DataHandler(new FileDataSource(file)));
            //configurando o nome do arquivo que pode ser diferente do arquivo
            //original Ex: setFileName("outroNome.png")
            attachment1.setFileName(file.getName());
            //adicionando o anexo na multipart
            multipart.addBodyPart(attachment1);
            //adicionando a multipart como conteudo da mensagem
            message.setContent(multipart);
            //enviando
            Transport.send(message);
            JOptionPane.showMessageDialog(null, "E-mail enviado!");

        } catch (AddressException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (MessagingException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
