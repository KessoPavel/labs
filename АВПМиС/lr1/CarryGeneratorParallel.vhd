library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity CarryGeneratorParallel is
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
end CarryGeneratorParallel;

architecture Behavioral of CarryGeneratorParallel is    
    
begin
   
    nG <= nG3 and (nG2 or nP2) and (nG1 or nP2 or nP1) and (nP3 or nP2 or nP1 or nP0);
    nP <=  nP3 or nP2 or nP1 or nP0;
    Cn_x <= (not nG0) or ((not nP0) and Cn);
    Cn_y <= (not nG1) or ((not nG0) and (not nP1)) or ((not nP1) and (not nP0) and Cn);
    Cn_z <= (not nG2) or ((not nG1) and (not nP2)) or ((not nG0) and (not nP2) and (not nP1)) or ((not nP2) and (not nP1) and (not nP0) and Cn); 
    
end Behavioral;