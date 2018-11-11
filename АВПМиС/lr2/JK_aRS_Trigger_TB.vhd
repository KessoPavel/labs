library IEEE;
use IEEE.STD_LOGIC_1164.ALL;


entity JK_aRS_Trigger_TB is
end JK_aRS_Trigger_TB;

architecture Behavioral of JK_aRS_Trigger_TB is
component JK_aRS_Trigger
    Port ( C : in STD_LOGIC;
           J : in STD_LOGIC;
           K : in STD_LOGIC;
           notR : in STD_LOGIC;
           notS : in STD_LOGIC;
           Q : out STD_LOGIC;
           notQ : out STD_LOGIC);
end component;

signal C : STD_LOGIC := '0';
signal J : STD_LOGIC := '1';
signal K : STD_LOGIC := '1';
signal notR : STD_LOGIC := '1';
signal notS : STD_LOGIC := '1';
signal Q : STD_LOGIC;
signal notQ : STD_LOGIC;

constant clk_period : time := 10 ns;

begin

uut : JK_aRS_Trigger port map(
		C => C,
		J => J,
		K => K,
		notR => notR,
		notS => notS,
		Q => Q,
		notQ => notQ);
		
clk_process :process
  begin
    C <= '0';
  wait for clk_period/2;
    C <= '1';
  wait for clk_period/2;
end process;

stim_proc: process
begin  

    wait for clk_period + clk_period/3;
    notR <= '0';
    wait for clk_period;
    notR <= '1';
	wait for clk_period*2;
    notS <= '0';
    wait for clk_period;
    notS <= '1';
    J <= '0';
    wait for clk_period;
    J <= '1';
    K <= '0';
    wait for clk_period;
    K <= '1';
end process;  

          		
end Behavioral;
