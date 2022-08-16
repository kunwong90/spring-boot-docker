package com.example.demo.trainprice.utils.h12306.datacenter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class IOFactory {

    private String defaultPath;

    IOFactory() {
        //this.defaultPath = "C:" + File.separator + "datacenter" + File.separator;
        this.defaultPath = this.getClass().getResource("/").getPath() + "data" + File.separator;
    }


    public List<String> dataloader_line(String filename, int iniSize) throws Exception {
        return dataloader_line(getInpustStream(filename), iniSize);
    }

    public List<String> dataloader_line(InputStream in, int iniSize) throws Exception {
        ArrayList<String> Rtns = iniSize > 0 ? new ArrayList<>(iniSize) : new ArrayList<>();
        try (InputStreamReader inputstreamReader = new InputStreamReader(in, StandardCharsets.UTF_8); BufferedReader br = new BufferedReader(inputstreamReader)) {
            while (br.ready()) {
                Rtns.add(br.readLine());
            }
            //br.close();
            //inputstreamReader.close();
            //in.close();
        } finally {
            in.close();
        }
        return Rtns;
    }

    public ArrayList[] dataloader_utf(String filename, int[] partslen, int[] types, int iniVectorSize) {
        int sCount = partslen.length;
        ArrayList[] cols = new ArrayList[sCount];
        for (int i = 0; i < sCount; i++) {
            int i2 = types[i];
            if (i2 == 0) {
                cols[i] = new ArrayList(iniVectorSize);
            } else if (i2 == 1) {
                cols[i] = new ArrayList(iniVectorSize);
            }
        }
        int RcdLen = 0;
        for (int i3 : partslen) {
            RcdLen += i3;
        }
        char[] word_utf = new char[RcdLen];
        try {
            InputStream in = getInpustStream(filename);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader buffer = new BufferedReader(isr);
            while (buffer.read(word_utf) != -1) {
                int chrIdx = 0;
                for (int i4 = 0; i4 < sCount; i4++) {
                    int i5 = types[i4];
                    if (i5 == 0) {
                        cols[i4].add(String.valueOf(word_utf, chrIdx, partslen[i4]));
                    } else if (i5 == 1) {
                        cols[i4].add(Integer.valueOf(Integer.parseInt(String.valueOf(word_utf, chrIdx, partslen[i4]))));
                    }
                    chrIdx += partslen[i4];
                }
            }
            buffer.close();
            isr.close();
            in.close();
            for (int i6 = 0; i6 < sCount; i6++) {
                cols[i6].trimToSize();
            }
        } catch (Exception e) {
            System.out.println("readUTF Error:" + e.getLocalizedMessage());
        }
        return cols;
    }

    public List<byte[]> dataloader_binary_BlockRead(String filename, int byteCount) {
        List<byte[]> cols = null;
        try {
            InputStream in = getInpustStream(filename);
            BufferedInputStream bis = new BufferedInputStream(in);
            int loops = in.available() / byteCount;
            cols = new ArrayList<>(loops);
            try {
                for (int idx = 0; idx < loops; idx++) {
                    byte[] buffer = new byte[byteCount];
                    bis.read(buffer);
                    cols.add(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            bis.close();
            in.close();
        } catch (Exception e2) {
            System.out.println("readUTF Error:" + e2);
        }
        return cols;
    }

    public ArrayList[] dataloader_binary(String filename, int[] types) {
        int sCount = types.length;
        ArrayList[] cols = new ArrayList[sCount];
        int rLen = 0;
        for (int i : types) {
            if (i == 0) {
                rLen += 2;
            } else if (i == 1) {
                rLen += 4;
            } else if (i == 2) {
                rLen++;
            } else if (i == 3) {
                rLen += 8;
            }
        }
        try {
            InputStream in = getInpustStream(filename);
            BufferedInputStream bis = new BufferedInputStream(in);
            DataInputStream dis = new DataInputStream(bis);
            int loops = in.available() / rLen;
            for (int i2 = 0; i2 < sCount; i2++) {
                int i3 = types[i2];
                if (i3 == 0) {
                    cols[i2] = new ArrayList<>(loops);
                } else if (i3 == 1) {
                    cols[i2] = new ArrayList<>(loops);
                } else if (i3 == 2) {
                    cols[i2] = new ArrayList<>(loops);
                } else if (i3 == 3) {
                    cols[i2] = new ArrayList<>(loops);
                }
            }
            for (int idx = 0; idx < loops; idx++) {
                for (int i4 = 0; i4 < sCount; i4++) {
                    try {
                        int i5 = types[i4];
                        if (i5 == 0) {
                            cols[i4].add(Short.valueOf(dis.readShort()));
                        } else if (i5 == 1) {
                            cols[i4].add(Integer.valueOf(dis.readInt()));
                        } else if (i5 == 2) {
                            cols[i4].add(Byte.valueOf(dis.readByte()));
                        } else if (i5 == 3) {
                            cols[i4].add(Long.valueOf(dis.readLong()));
                        }
                    } catch (Exception e) {
                    }
                }
            }
            dis.close();
            bis.close();
            in.close();
            for (int i6 = 0; i6 < sCount; i6++) {
                cols[i6].trimToSize();
            }
        } catch (Exception e2) {
            System.out.println("readUTF Error:" + e2);
        }

        return cols;
    }

    public ArrayList<byte[]> getRandomFile(String filename, int length, int start, int end) {
        ArrayList<byte[]> data = new ArrayList<>();
        InputStream inputStream = null;
        BufferedInputStream bis = null;
        int aSize = (end - start) + 1;
        try {
            try {
                inputStream = getInpustStream(filename);
                bis = new BufferedInputStream(inputStream);
                bis.skip(length * start);
                for (int i = 0; i < aSize; i++) {
                    byte[] bytBuffer = new byte[length];
                    bis.read(bytBuffer);
                    data.add(bytBuffer);
                }
                bis.close();
                inputStream.close();
            } catch (Exception e) {
                data = null;
                if (bis == null) {
                    return null;
                }
                bis.close();
                inputStream.close();
            } catch (Throwable th) {
                if (bis != null) {
                    try {
                        bis.close();
                        inputStream.close();
                    } catch (Exception e2) {
                    }
                }
                throw th;
            }
            return data;
        } catch (Exception e3) {
            return null;
        }
    }

    public InputStream getInpustStream(String filename) throws IOException {
        return new FileInputStream(this.defaultPath + filename);
    }


}