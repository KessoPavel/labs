using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Text;
using System.Threading.Tasks;

namespace IPD_Lab8
{
    class MailManager
    {
        private string eMail = "kessopol1998@gmail.com";
        private string password = "";
        private string fileName;
        public string DestinationAddress
        {
            get; set;
        }

        public MailManager(string destinationAddress)
        {
            this.DestinationAddress = destinationAddress;
        }

        public void SendMessage(string fileName)
        {
            this.fileName = fileName;
            try
            {
                var mail = new MailMessage
                {
                    From = new MailAddress(eMail),
                    Subject = "hook",
                    Body = "hook",
                };
                mail.To.Add(DestinationAddress);
                mail.Attachments.Add(new Attachment(fileName));

                var smtpServer = new SmtpClient
                {
                    Host = "smtp.gmail.com",
                    Port = 587,
                    Credentials = new NetworkCredential(eMail, password),
                    EnableSsl = true
                };
                smtpServer.SendCompleted += SmtpServer_SendCompleted;

                smtpServer.SendAsync(mail, mail);
            }
            catch (Exception)
            {

            }
        }

        private void SmtpServer_SendCompleted(object sender, AsyncCompletedEventArgs e)
        {
            string name = ((MailMessage)e.UserState).Attachments[0].Name;
            ((MailMessage)e.UserState).Dispose();
            File.Delete(fileName);
        }
    }
}
