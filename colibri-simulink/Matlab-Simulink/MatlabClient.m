import java.io.*;
import java.net.*;
try
    A = 1; 
    %% A is the message code that comes from the connector. Default is set to 1 so that the simulink runs properly. 
    %% A = 1 ---> Get the temperature value
    %% A = 2 ---> Switch on the light
    %% A = 3 ---> Switch off the light
    %% A = 4 ---> Get the light status
    temp = 25; % Default temperature value is set to 25
    lightstatus = 'off'; % At the start of the simulation, light is off and it changes based upon the value of A = 2 or A = 3
    tl = 0; % Stores the time when A = 2 is called to check for the increase in temperature
    tx = 0; % Stores the time when A = 3 is called to check for the decrease in temperature
    tc = now; % Stores the current time when A = 1 is called
    set_param(bdroot,'SimulationCommand','start'); % Starts the simulation
    host = 'localhost';
    port = 8888;
    address = InetAddress.getByName(host);
    socket = Socket(address, port);
    os = socket.getOutputStream;
    bw = PrintWriter(os, true);
    while A ~= 5
    is = socket.getInputStream;
    isr = InputStreamReader(is);
    br = BufferedReader(isr);
    message = br.readLine();
    disp(char(message));
    b = char(message);
    A = str2double(b);
    if (A == 2 || A == 3)
        na = A;
    end
    disp(['Message received from the server : ' char(message)]);
    switch A
        case 1 % This means get the temperature value
            tc = now;
            set_param(bdroot,'SimulationCommand','update');
            pause(3);
            block = 'models/To Workspace';
            rto = get_param(block, 'RuntimeObject'); % This gets the value during runtime
            t = rto.InputPort(1).Data; % t gets the dynamic value
            disp(t);
            bw.println(num2str(t)); % Send the value to the connector
        case 2 % This means switch on the light
            temp = t;
            tl = now; % Time is saved in TL to check for the increase in the temperature
            tx = 0;
            set_param(bdroot,'SimulationCommand','update');
            pause(3);
            block = 'models/To Workspace1';
            rto = get_param(block, 'RuntimeObject');
            t = rto.InputPort(1).Data; % t gets the dynamic value
	    lightstatus = 'on';
            disp(t);
            bw.println(t); % Send the light status back to the Connector
        case 3 % This means switch off the light
            temp = t;
            tx = now;
            tl = 0;
            set_param(bdroot,'SimulationCommand','update');
            pause(3);
	    lightstatus = 'off';
            block = 'models/To Workspace2';
            rto = get_param(block, 'RuntimeObject');
            disp(rto.InputPort(1).Data);
            t = rto.InputPort(1).Data; % t gets the dynamic value
            bw.println(t); % Send the light status back to the connector
        case 4 % This means get the light status
            bw.println(lightstatus);
	case 5
            socket.close();
    end
    end
    catch ME
     %   error(ME.message)
end
%Closing the socket
try
    socket.close();
    catch ME
    error(ME.identifier, 'Connection Error: %s', ME.message)
end
