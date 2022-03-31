package info.istamendil.notebook.data;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation db with storing objects on hard drive.
 *
 * @author Alexander Ferenets (Istamendil) <ist.kazan@gmail.com>
 * <p>
 * Code for studying purposes. Programming course. Kazan Federal
 * University, ITIS. http://study.istamendil.info/
 */
public class FileDb implements Db {

    protected final String path;

    public FileDb(String path) {
        this.path = path;
    }

    @Override
    public void save(Object obj) throws DbException {
        Object[] data = findAll();
        try (FileOutputStream stream = new FileOutputStream(this.path)) {
            Object[] newData = new Object[data.length + 1];
            System.arraycopy(data, 0, newData, 0, data.length);
            newData[newData.length - 1] = obj;
            System.out.println("cant save");
            stream.write(convertToBytes(newData));
        } catch (IOException ex) {
            throw new DbException("DB error: " + ex.getMessage());
        }
    }

    @Override
    public Object[] findAll() throws DbException {
        try {
            Path path = Paths.get(this.path);
            byte[] data = Files.readAllBytes(path);
            if (data.length > 0) {
                return (Object[]) this.convertFromBytes(data);
            } else {
                return new Object[0];
            }
        } catch (IOException ex) {
            throw new DbException("DB error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new DbException("DB error: " + ex.getMessage());
        }
    }

    private byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos))
        {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    private Object convertFromBytes(byte[] data) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInput in = new ObjectInputStream(bis))
        {
            return in.readObject();
        }
    }

    @Override
    public void removeAll() throws DbException {
        try (FileOutputStream stream = new FileOutputStream(this.path)) {
            PrintWriter writer = new PrintWriter(stream);
            writer.write("");
        } catch (IOException ex) {
            throw new DbException("DB error: " + ex.getMessage());
        }
    }

    @Override
    public void remove(int index) throws DbException, ClassNotFoundException {
        try {
            Path path = Paths.get(this.path);
            byte[] data = Files.readAllBytes(path);
            Object[] currdata = (Object[]) convertFromBytes(data);
            List<Object> newData;
            int size = currdata.length;
            if(index > size){
                return;
            }
            else if(index == 0){
                newData = Arrays.stream(currdata).skip(1).collect(Collectors.toList());
            } else if(index == size){
                newData = Arrays.stream(currdata).limit(size - 1).collect(Collectors.toList());
            }else {
                newData = (List<Object>) Arrays.stream(currdata).collect(Collectors.toList());
                newData.remove(index);
                }
            removeAll();
            size--;
            for (int i = 0; i <size ; i++) {
                save(newData.get(i));
            }

        } catch (IOException ex) {
            throw new DbException("DB error: " + ex.getMessage());
        }
    }
}
