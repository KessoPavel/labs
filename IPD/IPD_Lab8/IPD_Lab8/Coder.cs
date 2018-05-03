using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IPD_Lab8
{
    class Coder
    {

        public static string Code(string str)
        {
            string answer = "";
            foreach (char c in str.ToArray())
            {
                answer += Code(c) + '|';
            }

            return answer.Substring(0, answer.Length - 1);
        }


        public static string Decode(string str)
        {
            string answer = "";
            string[] latters = str.Split('|');

            foreach (string s in latters)
            {
                string[] split = s.Split('-');

                int ascii = 0;

                foreach (string l in split)
                {
                    ascii *= 10;
                    try
                    {
                        ascii += ReversFib(int.Parse(l)) - 2;
                    }
                    catch (FormatException)
                    {
                        return null;
                    }
                }

                char later = (char)(ascii);

                answer += later;
            }
            return answer;
        }

        private static string Code(char c)
        {
            string answer = "";
            foreach (char h in ((int)c).ToString().ToArray())
            {
                answer += (Fib(h - '0' + 2)).ToString() + "-";
            }

            return answer.Substring(0, answer.Length - 1);
        }


        private static int Fib(int n)
        {
            return n > 1 ? Fib(n - 1) + Fib(n - 2) : n;
        }

        private static int ReversFib(int n)
        {
            return ReversFib(n, 1);
        }

        private static int ReversFib(int n, int a)
        {
            return n > 0 ? ReversFib(n - Fib(a), a + 1) : a;
        }
    }
}
