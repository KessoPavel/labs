library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity JK_aRS_Trigger is
    Port ( C : in STD_LOGIC;
           J : in STD_LOGIC;
           K : in STD_LOGIC;
           notR : in STD_LOGIC;
           notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end JK_aRS_Trigger;

architecture Behavioral of JK_aRS_Trigger is

signal tempQ : STD_LOGIC;

begin
    process (C, notR, notS)
	begin
		if notR = '0' then
			Q <= '0';
			tempQ <= '0';
			notQ <= '1';
		else if notS = '0' then
			Q <= '1';
			tempQ <= '1';
			notQ <= '0';
		else if C'event and C = '0' then
			if J = '0' and K = '1' then
			    Q <= '0';
			    tempQ <= '0';
                notQ <= '1';
            else if J = '1' and K = '0' then
                Q <= '1';
                tempQ <= '1';
                notQ <= '0';
            else if J = '1' and K = '1' then
                Q <= not tempQ;
                notQ <= tempQ;
                tempQ <= not tempQ;
            end if;
            end if;
            end if;    
		end if;
		end if;
		end if;
	end process;
end Behavioral;
