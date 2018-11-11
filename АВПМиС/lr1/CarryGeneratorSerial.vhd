library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity CarryGeneratorSerial is
    Port ( nP3 : in STD_LOGIC;
           nG3 : in STD_LOGIC;
           nP2 : in STD_LOGIC;
           nG2 : in STD_LOGIC;
           nP1 : in STD_LOGIC;
           nG1 : in STD_LOGIC;
           nP0 : in STD_LOGIC;
           nG0 : in STD_LOGIC;
           Cn : in STD_LOGIC;
           nP : out STD_LOGIC;
           nG : out STD_LOGIC;
           Cn_z : out STD_LOGIC;
           Cn_y : out STD_LOGIC;
           Cn_x : out STD_LOGIC);
end CarryGeneratorSerial;

architecture Behavioral of CarryGeneratorSerial is    
    
begin
    process (nG3, nG2, nG1,nP3, nP2, nP1, nP0)
	begin
		if nG3 = '0' or (nG2 = '0' and nP2 = '0') or (nG1 = '0' and nP2 = '0' and nP1 = '0') or  (nP3 = '0' and nP2 = '0' and nP1 = '0' and nP0 = '0')  then
			nG <= '0';
		else 
			nG <= '1';
		end if;
	end process;
    
    process (nP3, nP2, nP1, nP0)
	begin
		if (nP3 = '0' and nP2 = '0' and nP1 = '0' and nP0 = '0') then
			nP <= '0';
		else 
			nP <= '1';
		end if;
	end process;
	
	process (nG0, nP0, Cn)
	begin
		if nG0 = '0' or ( nP0 = '0' and Cn = '1') then
			Cn_x <= '1';
		else
			Cn_x <= '0';
		end if;
	end process;
	
	process (nG1, Cn, nG0, nP1, nP0)
	begin
		if nG1 = '0' or (nG0 = '0' and nP1 = '0') or (nP1 = '0' and nP0 = '0' and Cn = '1')  then
			Cn_y <= '1';
		else 
			Cn_y <= '0';
		end if;
	end process;
	
	process (nG2, Cn, nG1, nG0, nP2, nP1, nP0)
	begin
		if nG2 = '0' or (nG1 = '0' and nP2 = '0') or (nG0= '0' and nP2 = '0' and nP1 = '0')  or (nP2 = '0' and nP1 = '0' and nP0 = '0' and Cn = '1') then
			Cn_z <= '1';
		else 
			Cn_z <= '0';
		end if;
	end process;   
end Behavioral;