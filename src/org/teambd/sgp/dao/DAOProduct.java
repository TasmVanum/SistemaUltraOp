package org.teambd.sgp.dao;

import org.teambd.sgp.models.PriceHistory;
import org.teambd.sgp.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOProduct implements DAO<Product> {

    // TODO: crear funcion que recupere el producto
    // TODO: Mostrar solamente los datos marcados como activos

    private MyConnection myConnection;

    public DAOProduct(MyConnection connection) {this.myConnection = connection;}
    @Override
    public List<Product> getAll() throws SQLException {
        String sql = "SELECT id, name, description, brand_id_fk, category_id_fk, elaboration_date, " +
                "expiration_date, gross_price, net_price, stock, is_great, is_active " +
                "FROM product "+
                "WHERE is_active = 1 "+
                "ORDER BY id ASC";
        List<Product> products;
        ResultSet resultSet = myConnection
                .getConnection()
                .createStatement()
                .executeQuery(sql);

        if ( resultSet.next() ) {
            products = new ArrayList<>();
            do {
                try {


                products.add(new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("brand_id_fk"),
                        resultSet.getInt("category_id_fk"),
                        resultSet.getDate("elaboration_date"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getInt("gross_price"),
                        resultSet.getInt("net_price"),
                        resultSet.getInt("stock"),
                        resultSet.getBoolean("is_great"),
                        resultSet.getBoolean("is_active")
                ));
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } while (resultSet.next() );
        }else {
            products = null;
        }
        return products;
    }

    @Override
    public Product getById(int id) throws SQLException {
        String sql = "SELECT id, name, description, brand_id_fk, category_id_fk, elaboration_date, " +
                "expiration_date, gross_price, net_price, stock, is_great, is_active " +
                "FROM product "+
                "WHERE id = ? ";

        Product product;

        PreparedStatement ps = myConnection
                .getConnection()
                .prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if ( rs.next() ) {
            product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("brand_id_fk"),
                    rs.getInt("category_id_fk"),
                    rs.getDate("elaboration_date"),
                    rs.getDate("expiration_date"),
                    rs.getInt("gross_price"),
                    rs.getInt("net_price"),
                    rs.getInt("stock"),
                    rs.getBoolean("is_great"),
                    rs.getBoolean("is_active")
            );
        } else {
            product= null;
        }
        return product;
    }

    @Override
    public int insert(Product product) throws SQLException {
        String sql = "INSERT INTO product " +
                "(name, description, brand_id_fk, category_id_fk, elaboration_date, expiration_date, " +
                "gross_price, stock, is_great) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = myConnection
                .getConnection()
                .prepareStatement(sql);
        ps.setString(1, product.getName());
        ps.setString(2, product.getDescription());
        ps.setInt(3, product.getBrandIdFk());
        ps.setInt(4, product.getCategoryIdFk());
        ps.setDate(5, product.getElaborationDate());
        ps.setDate(6, product.getExpirationDate());
        ps.setInt(7, product.getGrossPrice());
        ps.setInt(8, product.getStock());
        ps.setInt(9, product.isGreat() ? 1 : 0);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    @Override
    public int update(Product product) throws SQLException {
        String sql = "UPDATE product SET name = ?, description = ?, brand_id_fk = ?, category_id_fk = ?, elaboration_date = ?, " +
                "expiration_date = ?,  gross_price = ?,  net_price = ?,  stock = ?, is_great = ? "+
                "WHERE id = ?";

        PreparedStatement preparedStatement = myConnection.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setInt(3, product.getBrandIdFk());
        preparedStatement.setInt(4, product.getCategoryIdFk());
        preparedStatement.setDate(5, product.getElaborationDate());
        preparedStatement.setDate(6, product.getExpirationDate());
        preparedStatement.setInt(7, product.getGrossPrice());
        preparedStatement.setInt(8, product.getNetPrice());
        preparedStatement.setInt(9, product.getStock());
        preparedStatement.setInt(10, product.isGreat() ? 1 : 0);
        preparedStatement.setInt(11, product.getId());

        int rowAffected = preparedStatement.executeUpdate();

        return rowAffected;
    }

    // TODO: En vez de hacer un delete, hacer un update
    @Override
    public int delete(int id) throws SQLException {
        Product product = new Product();

        String sql = "UPDATE product SET is_active = 0 "+
                "WHERE id = ?";
        PreparedStatement preparedStatement = myConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, product.getId());

        int rowAffected = preparedStatement.executeUpdate();

        return rowAffected;
    }

    public int activate(int id) throws SQLException {
        Product product = new Product();

        String sql = "UPDATE product SET is_active = 1 "+
                "WHERE id = ?";
        PreparedStatement preparedStatement = myConnection.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, product.getId());

        int rowAffected = preparedStatement.executeUpdate();

        return rowAffected;
    }

    public List<Product> getAllCachero() throws SQLException {
        String sql = "SELECT id, name, description, brand_id_fk, category_id_fk, elaboration_date, " +
                "expiration_date, gross_price, net_price, stock, is_great, is_active " +
                "FROM product "+
                "WHERE is_active = 1 AND is_great = 1"+
                "ORDER BY id ASC";
        List<Product> products2;
        ResultSet rs = myConnection
                .getConnection()
                .createStatement()
                .executeQuery(sql);
        if ( rs.next() ) {
            products2 = new ArrayList<>();
            do {
                products2.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("brand_id_fk"),
                        rs.getInt("category_id_fk"),
                        rs.getDate("elaboration_date"),
                        rs.getDate("expiration_date"),
                        rs.getInt("gross_price"),
                        rs.getInt("net_price"),
                        rs.getInt("stock"),
                        rs.getBoolean("is_great"),
                        rs.getBoolean("is_active")
                ));
            } while (rs.next() );
        }else {
            products2 = null;
        }
        return products2;
    }


}
