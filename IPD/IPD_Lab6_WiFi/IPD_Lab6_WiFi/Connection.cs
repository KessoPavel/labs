using System;
using SimpleWifi;

namespace IPD_Lab6_WiFi
{
    class Connection
    {
        private AccessPoint accessPoint;
        private string mac;
       
        public Connection() { }

        public Connection(AccessPoint accessPoint, string mac)
        {
            this.accessPoint = accessPoint;
            this.mac = mac;
        }        

        public string Mac
        {
            get { return mac; }
            set { this.mac = value; }
        }

        public string SSID
        {
            get { return accessPoint.Name.Equals("") ? null : accessPoint.Name; }
        }

        public int SignalStrength
        {
            get { return (int)accessPoint.SignalStrength; }
        }

        public bool IsConnected
        {
            get { return accessPoint.IsConnected; }
        }

        public string AuthType
        {
            get
            {
                var cipherAlgorithm = accessPoint.ToString().Split()[10];
                var authAlgorithm = accessPoint.ToString().Split()[6];
                switch (cipherAlgorithm)
                {
                    case "None":
                        return "Open";
                    case "Wep":
                        return "Wep";
                    case "CCMP":
                    case "TKIP":
                        return (authAlgorithm.Equals("RSNA") ? "WPA2-Enterprise-PEAP-MSCHAPv2" : "WPA2-PSK");
                    default:
                        return "Unknown";
                }
            }
        }

        public void ConnectAsync(AuthRequest authRequest, Action<bool> onConnectComplete)
        {
            accessPoint.ConnectAsync(authRequest, true, onConnectComplete);
        }

        public bool Equals(Connection connection)
        {
            return this.SSID.Equals(connection.SSID) && (this.IsConnected == connection.IsConnected);
        }

        public void Connect(string password, Action<bool> onConnectComplite)
        {
            var authRequest = new AuthRequest(accessPoint)
            {
                Password = password
            };
            ConnectAsync(authRequest, onConnectComplite);
        }

        public static void Disconect()
        {
            new Wifi().Disconnect();
        }
    }
}
