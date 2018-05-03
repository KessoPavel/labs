using EventHook;
using System;
using System.Text.RegularExpressions;
using System.Windows.Forms;

namespace IPD_Lab8
{
    class Hooks
    {
        private const string specialSymbolsPattern = "^[^a-zA-Zа-яА-Я]?$";
        public bool IsStarted
        {
            get; set;
        }

        public delegate void AddFromFile(string line);
        public delegate void ChangeVisable();
        public delegate void FabeMonitor();

        public event AddFromFile MauseHook;
        public event AddFromFile KeyBordHook;
        public event ChangeVisable Visable;
        public event FabeMonitor FabeMonitorEvent;
        
        private bool LeftShiftPressed;

        public Hooks()
        {
            MouseWatcher.OnMouseInput += (s, e) =>
            {
                if (IsMouseButtonClickEvent(e.Message.ToString()))
                {
                    MauseHook?.Invoke(GetMouseEventDescription(e));
                }
            };

            KeyboardWatcher.OnKeyInput += (s, e) =>
            {
                if (IsButtonDownEvent(e.KeyData.EventType.ToString()))
                {
                    KeyBordHook?.Invoke(GetKeyBordEventDescription(e.KeyData));
                    KeyCombinationAnalyzer(e.KeyData);
                }
            };
        }

        public void Start()
        {
            MouseWatcher.Start();
            KeyboardWatcher.Start();
            IsStarted = true;
        }

        public void Stop()
        {
            MouseWatcher.Stop();
            KeyboardWatcher.Stop();
            IsStarted = false;
        }

        private bool IsMouseButtonClickEvent(string message) =>
            (message.Equals("WM_LBUTTONDOWN") || message.Equals("WM_RBUTTONDOWN"));


        private bool IsButtonDownEvent(string message) =>
            message.Equals("down");


        private string GetMouseEventDescription(EventHook.MouseEventArgs e) =>
            DateTime.Now.ToString() + ": " + (e.Message.ToString().Equals("WM_LBUTTONDOWN") ? "left button " : "rihgt button ") + "(" + e.Point.x + "," + e.Point.y + ")\r\n";


        private string GetKeyBordEventDescription(KeyData e) =>
            DateTime.Now.ToString() + ": Key - " + GetKey(e) + "(" + e.EventType + ")\r\n";


        private string GetKey(KeyData keyData) =>
            (Regex.IsMatch(keyData.UnicodeCharacter, specialSymbolsPattern)) ?
                keyData.Keyname : keyData.UnicodeCharacter;

        private void KeyCombinationAnalyzer(KeyData key)
        {
            switch (key.Keyname)
            {
                case "LeftShift":
                    LeftShiftPressed = true;
                    break;

                case "F1":
                    if (LeftShiftPressed)
                    {
                        Visable?.Invoke();
                    }
                    break;

                case "F2":
                    if (LeftShiftPressed)
                    {
                        FabeMonitorEvent?.Invoke();
                    }
                    break;
                default:
                    LeftShiftPressed = false;
                    break;
            }
        }
    }
}
