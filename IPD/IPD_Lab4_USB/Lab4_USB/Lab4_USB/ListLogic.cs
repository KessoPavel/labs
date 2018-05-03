using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4
{
    class ListLogic
    {
        public static bool comparer(List<Device> a, List<Device> b)
        {
            if (a == null || b == null || a.Count != b.Count)
                return false;
            for(int i = 0; i < a.Count; i++)
            {
                if (!a[i].Equals(b[i]))
                    return false;
            }
            return true;
        }
    }
}
