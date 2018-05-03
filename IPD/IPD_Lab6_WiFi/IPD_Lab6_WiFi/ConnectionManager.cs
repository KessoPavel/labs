using System;
using System.Collections.Generic;
using SimpleWifi;
using System.Diagnostics;

namespace IPD_Lab6_WiFi
{
    class ConnectionManager
    {
        static public List<Connection> GetConnectionList()
        {
            List<Connection> answer = new List<Connection>();

            string[] bssids = GetBssids();

            try
            {
                Wifi wifi = new Wifi();
                
                foreach(var accessPoint in wifi.GetAccessPoints())
                {
                    answer.Add(new Connection(accessPoint, GetMac(bssids, accessPoint)));
                }
            }
            catch (System.ComponentModel.Win32Exception)
            {
                answer = null;
            }

            return answer;
        }

        static private string[] GetBssids()
        {
            string[] bssids;

            var process = new Process
            {
                StartInfo = new ProcessStartInfo
                {
                    CreateNoWindow = true,
                    FileName = "cmd",
                    Arguments = @"/C ""netsh wlan show networks mode=bssid | findstr SSID""",
                    RedirectStandardOutput = true,
                    UseShellExecute = false
                }
            };
            process.Start();
            bssids = process.StandardOutput.ReadToEnd().Replace(" ", "").Replace("\r", "").Split('\n');
            process.WaitForExit();

            return bssids;
        }

        static private string GetMac(string[] bssids, AccessPoint accessPoint)
        {
            for(int i = 0; i < bssids.Length; i++)
            {
                if((bssids[i].Split(':')[0].Contains("SSID") && accessPoint.Name.Equals(bssids[i].Split(':')[1])))
                {
                    return bssids[i + 1].Remove(0, bssids[i + 1].IndexOf(":") + 1);
                }
            }
            return null;
        }

        static public bool Equals(List<Connection> a, List<Connection> b)
        {
            if (a == null || b == null || a.Count != b.Count)
                return false;
            for (int i = 0; i < a.Count; i++)
            {
                if (!a[i].Equals(b[i]))
                    return false;
            }
            return true;
        }
    }
}
