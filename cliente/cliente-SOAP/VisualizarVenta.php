<?php 
    $conexion=mysqli_connect('localhost','root','','ventas_2021_ves'); 
?>

<!DOCTYPE html>
<html>
    <head>
        <title>Visualizar Ventas</title>
    </head>
    <body>
        <br>
            <table>
                <tr>
                    <td>Nombre</td>
                    <td>Descripción</td>
                    <td>Precio</td>
                    <td>Stock</td>
                </tr>

                <?php
                $sql="SELECT * from productosv"; 

                white($mostrar=mysqli_fetch_array($result)){
                    ?> 
                

                <tr>
                    <td><?php echo $mostrar['nombre']?></td>
                    <td><?php echo $mostrar['descripcion']?></td>
                    <td><?php echo $mostrar['preciou']?></td>
                    <td><?php echo $mostrar['en_stock']?></td>
                </tr>

                <?php 
                    }
                ?>
            </table>
    </body>
</html>