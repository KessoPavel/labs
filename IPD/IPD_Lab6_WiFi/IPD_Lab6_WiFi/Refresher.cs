using System;
using System.Management;
using System.Windows.Forms;

namespace IPD_Lab6_WiFi
{
    class Refresher
    {
        private Timer Timer;

        public Refresher()
        {
            Timer = new Timer
            {
                Interval = (10000)
            };
        }

        public void AddListerenToTimer(EventHandler handler)
        {
            Timer.Tick += handler;
        }

        public void Start()
        {
            Timer.Start();
        }

    }
}
