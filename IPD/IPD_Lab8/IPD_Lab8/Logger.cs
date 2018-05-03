using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace IPD_Lab8
{
    class Logger
    {
        public delegate void SendEmail(string fileName); // delegat - переменная, хранящая ссылку на метод
        public event SendEmail MessegeReady;

        private string folderPath;

        private int numberOfMouseFile;
        private int numberOfKeyBordFile;
        private int maxFileSize;

        public int MaxFileSize
        {
            get { return maxFileSize; }
            set { this.maxFileSize = value; }
        }

        public Logger(string folderPath, int maxFileSize)
        {
            this.folderPath = folderPath;
            this.maxFileSize = maxFileSize;

            this.numberOfKeyBordFile = GetNumberOfKeyBordLogFile();
            this.numberOfMouseFile = GetNumberOfMouseLogFile();
        }

        public void AddLineFromMouseFiel(string line)
        {
            string filePath = folderPath + "\\MauseLog" + numberOfMouseFile;

            if (!File.Exists(filePath))
            {
                File.AppendAllText(filePath, line);
                return;
            }

            if (!CheckFileSize(filePath, line.Length))
            {
                MessegeReady?.Invoke(filePath);
                numberOfMouseFile++;
                filePath = folderPath + "\\MauseLog" + numberOfMouseFile;
            }

            File.AppendAllText(filePath, line);
        }

        public void AddLineFromKeyBordFile(string line)
        {
            string filePath = folderPath + "\\KeyBordLog" + numberOfKeyBordFile;

            if (!File.Exists(filePath))
            {
                File.AppendAllText(filePath, line);
                return;
            }

            if (!CheckFileSize(filePath, line.Length))
            {
                MessegeReady?.Invoke(filePath);
                numberOfKeyBordFile++;
                filePath = folderPath + "\\KeyBordLog" + numberOfKeyBordFile;
            }

            File.AppendAllText(filePath, line);
        }

        private bool CheckFileSize(string fileName, int size)
        {
            if (FileIsAvailable(fileName))
            {
                FileInfo fileInfo = new FileInfo(fileName);
                return (fileInfo.Length + size) <= maxFileSize;
            }
            return false;
        }


        private bool FileIsAvailable(string fileName)
        {
            if (File.Exists(fileName))
            {
                if (File.GetAttributes(fileName) != FileAttributes.ReadOnly)
                {
                    return true;
                }
            }
            return false;
        }

        private int GetNumberOfKeyBordLogFile()
        {
            string[] files = Directory.GetFiles(folderPath, "KeyBordLog*");
            List<int> number = new List<int>();

            foreach (var s in files)
            {
                number.Add(int.Parse(s.Remove(0, (folderPath + "\\KeyBordLog").Length)));
            }

            return number.Count > 0 ? number.Max() : 0;
        }

        private int GetNumberOfMouseLogFile()
        {
            string[] files = Directory.GetFiles(folderPath, "MauseLog*");
            List<int> number = new List<int>();

            foreach (var s in files)
            {
                number.Add(int.Parse(s.Remove(0, (folderPath + "\\MauseLog").Length)));
            }

            return number.Count > 0 ? number.Max() : 0;
        }
    }
}
