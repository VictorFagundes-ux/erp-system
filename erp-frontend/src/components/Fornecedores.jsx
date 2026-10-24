import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function Fornecedores() {
    const [fornecedores, setFornecedores] = useState([]);
    const [loading, setLoading] = useState(true);
    const [formData, setFormData] = useState({ name: '', cnpj: '', email: '', phone: '' });

    useEffect(() => {
        fetchFornecedores();
    }, []);

    const fetchFornecedores = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get('http://localhost:8080/api/suppliers', {
                headers: { Authorization: `Bearer ${token}` }
            });
            setFornecedores(response.data);
        } catch (error) {
            console.error('Erro ao buscar fornecedores:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            await axios.post('http://localhost:8080/api/suppliers', formData, {
                headers: { Authorization: `Bearer ${token}` }
            });
            setFormData({ name: '', cnpj: '', email: '', phone: '' });
            fetchFornecedores();
        } catch (error) {
            console.error('Erro ao cadastrar fornecedor:', error);
        }
    };

    return (
        <div className="p-6 max-w-6xl mx-auto">
            <h2 className="text-2xl font-bold mb-6 text-gray-800">Gestão de Fornecedores</h2>

            {/* Formulário de Cadastro */}
            <form onSubmit={handleSubmit} className="bg-white p-4 rounded-lg shadow mb-6 grid grid-cols-1 md:grid-cols-4 gap-4">
                <input
                    type="text"
                    placeholder="Nome da Empresa"
                    value={formData.name}
                    onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                    className="border p-2 rounded"
                    required
                />
                <input
                    type="text"
                    placeholder="CNPJ"
                    value={formData.cnpj}
                    onChange={(e) => setFormData({ ...formData, cnpj: e.target.value })}
                    className="border p-2 rounded"
                    required
                />
                <input
                    type="email"
                    placeholder="E-mail de Contato"
                    value={formData.email}
                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                    className="border p-2 rounded"
                />
                <button type="submit" className="bg-blue-600 text-white p-2 rounded hover:bg-blue-700 font-semibold">
                    Cadastrar Fornecedor
                </button>
            </form>

            {/* Tabela de Listagem */}
            {loading ? (
                <p>A carregar fornecedores...</p>
            ) : (
                <div className="bg-white shadow rounded-lg overflow-hidden">
                    <table className="min-w-full divide-y divide-gray-200">
                        <thead className="bg-gray-50">
                        <tr>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nome</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">CNPJ</th>
                            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">E-mail</th>
                        </tr>
                        </thead>
                        <tbody className="divide-y divide-gray-200">
                        {fornecedores.map((f) => (
                            <tr key={f.id}>
                                <td className="px-6 py-4 whitespace-nowrap font-medium text-gray-900">{f.name}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-gray-600">{f.cnpj}</td>
                                <td className="px-6 py-4 whitespace-nowrap text-gray-600">{f.email}</td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
}