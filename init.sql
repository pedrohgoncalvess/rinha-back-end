create table if not exists clientes (
    id serial,
    limite integer not null,
    saldo integer not null default 0,

    constraint clientes_pk primary key (id)
);

create table if not exists transacoes (
    id serial,
    id_cliente integer,
    valor integer not null,
    tipo char(1) not null,
    descricao varchar(50) not null,
    realizada_em timestamp default current_timestamp,

    constraint transacoes_pk primary key (id),
    constraint transacoes_clientes_fk foreign key (id_cliente) references clientes(id)

);

CREATE OR REPLACE FUNCTION att_saldo()
    RETURNS TRIGGER AS $$
DECLARE
    novo_saldo DECIMAL;
BEGIN
    IF NEW.tipo = 'd' THEN
        novo_saldo := (SELECT saldo + NEW.valor FROM clientes WHERE id = NEW.id_cliente);
    ELSIF NEW.tipo = 'c' THEN
        novo_saldo := (SELECT saldo - NEW.valor FROM clientes WHERE id = NEW.id_cliente);
    END IF;

    IF novo_saldo < (SELECT limite * -1 FROM clientes WHERE id = NEW.id_cliente) THEN
        RAISE EXCEPTION 'Limite excedido';
    END IF;

    IF NEW.tipo = 'd' THEN
        UPDATE clientes
        SET saldo = novo_saldo
        WHERE id = NEW.id_cliente;
    ELSIF NEW.tipo = 'c' THEN
        UPDATE clientes
        SET saldo = novo_saldo
        WHERE id = NEW.id_cliente;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER transacao_trigger
    AFTER INSERT ON transacoes
    FOR EACH ROW
EXECUTE FUNCTION att_saldo();

insert into clientes (limite) values (100000);
insert into clientes (limite) values (80000);
insert into clientes (limite) values (1000000);
insert into clientes (limite) values (10000000);
insert into clientes (limite) values (500000);