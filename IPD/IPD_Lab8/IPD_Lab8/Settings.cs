using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace IPD_Lab8
{
    class Settings
    {
        private string eMail = "email@gmail.com";
        private int maxLogFileSize = 1024;
        private bool autoStart = false;

        public string EMAil
        {
            get { return eMail; }
            set { eMail = value; }
        }

        public int MaxLogFileSize
        {
            get { return maxLogFileSize; }
            set { maxLogFileSize = value; }
        }

        public bool AutoStart
        {
            get { return autoStart; }
            set { autoStart = value; }
        }

        public Settings()
        {
            string[] settings;

            try
            {
                settings = File.ReadAllLines(@"C:\IPD\settings");
            }
            catch (Exception)
            {
                return;
            }

            if (settings.Length == 3)
            {
                string eMail = Coder.Decode(settings[0]);
                string maxSize = Coder.Decode(settings[1]);
                string autoStart = Coder.Decode(settings[2]);

                if (ValidateeMail(eMail) && ValidateNumber(maxSize) && (autoStart.Equals("true") || autoStart.Equals("false")))
                {
                    this.eMail = Coder.Decode(settings[0]);
                    this.maxLogFileSize = int.Parse(Coder.Decode(settings[1]));
                    this.autoStart = Coder.Decode(settings[2]).Equals("true");
                }
            }
        }

        public bool SaveSettings()
        {
            if (ValidateeMail())
            {
                File.Delete(@"C:\IPD\settings");
                File.AppendAllLines(@"C:\IPD\settings", new List<string>() { Coder.Code(eMail), Coder.Code(maxLogFileSize.ToString()), Coder.Code(autoStart ? "true" : "false") });
                return true;
            }
            return false;
        }

        public bool ValidateeMail(string mail)
        {
            if (mail == null)
                return false;
            System.Text.RegularExpressions.Regex rEMail =
                new System.Text.RegularExpressions.Regex(@"^[a-zA-Z][\w\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\w\.-]*[a-zA-Z0-9]\.[a-zA-Z][a-zA-Z\.]*[a-zA-Z]$");
            if (mail.Length > 0)
            {
                return (rEMail.IsMatch(mail));
            }
            return false;
        }

        public bool ValidateeMail()
        {
            return ValidateeMail(eMail);
        }

        public bool ValidateNumber(string number)
        {
            foreach (var digit in number.ToArray())
            {
                if (!Char.IsDigit(digit))
                    return false;
            }
            return true;
        }
    }
}
