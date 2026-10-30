/**
 * ChurnGuard Pro - Main Application JavaScript
 * Handles sidebar toggle, dark mode, chart initialization helpers,
 * form interactions, and toast notifications.
 */

'use strict';

// ================================================================
// SIDEBAR TOGGLE
// ================================================================
const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('mainContent');
const sidebarToggle = document.getElementById('sidebarToggle');

function toggleSidebar() {
    if (!sidebar) return;
    if (window.innerWidth <= 900) {
        sidebar.classList.toggle('mobile-open');
    } else {
        sidebar.classList.toggle('collapsed');
        mainContent?.classList.toggle('sidebar-collapsed');
        localStorage.setItem('sidebarCollapsed', sidebar.classList.contains('collapsed'));
    }
}

// Restore sidebar state
if (sidebar && window.innerWidth > 900) {
    const collapsed = localStorage.getItem('sidebarCollapsed') === 'true';
    if (collapsed) {
        sidebar.classList.add('collapsed');
        mainContent?.classList.add('sidebar-collapsed');
    }
}

if (sidebarToggle) sidebarToggle.addEventListener('click', toggleSidebar);

// Close sidebar on outside click (mobile)
document.addEventListener('click', (e) => {
    if (window.innerWidth <= 900 && sidebar &&
        sidebar.classList.contains('mobile-open') &&
        !sidebar.contains(e.target) &&
        e.target !== sidebarToggle) {
        sidebar.classList.remove('mobile-open');
    }
});

// ================================================================
// DARK MODE
// ================================================================
const themeToggle = document.getElementById('themeToggle');
const root = document.documentElement;

function applyTheme(theme) {
    root.setAttribute('data-theme', theme);
    localStorage.setItem('theme', theme);
    if (themeToggle) {
        themeToggle.innerHTML = theme === 'dark'
            ? '<i class="bi bi-sun-fill"></i>'
            : '<i class="bi bi-moon-fill"></i>';
    }
}

// Restore theme
const savedTheme = localStorage.getItem('theme') || 'light';
applyTheme(savedTheme);

if (themeToggle) {
    themeToggle.addEventListener('click', () => {
        const current = root.getAttribute('data-theme') || 'light';
        applyTheme(current === 'dark' ? 'light' : 'dark');
    });
}

// ================================================================
// TOAST NOTIFICATIONS
// ================================================================
function showToast(message, type = 'success') {
    const container = document.getElementById('toastContainer') || createToastContainer();
    const toast = document.createElement('div');
    const icons = { success: 'check-circle-fill', danger: 'x-circle-fill', warning: 'exclamation-triangle-fill', info: 'info-circle-fill' };
    toast.className = `toast-item toast-${type}`;
    toast.innerHTML = `<i class="bi bi-${icons[type] || icons.info}"></i><span>${message}</span><button class="toast-close" onclick="this.parentElement.remove()"><i class="bi bi-x"></i></button>`;
    container.appendChild(toast);
    setTimeout(() => toast.classList.add('show'), 10);
    setTimeout(() => { toast.classList.remove('show'); setTimeout(() => toast.remove(), 300); }, 4000);
}

function createToastContainer() {
    const div = document.createElement('div');
    div.id = 'toastContainer';
    div.style.cssText = 'position:fixed;top:80px;right:20px;z-index:9999;display:flex;flex-direction:column;gap:8px;min-width:300px;';
    document.body.appendChild(div);
    return div;
}

// Show flash messages from server as toasts
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('[data-flash-success]').forEach(el => showToast(el.dataset.flashSuccess, 'success'));
    document.querySelectorAll('[data-flash-error]').forEach(el => showToast(el.dataset.flashError, 'danger'));
});

// ================================================================
// DELETE CONFIRMATION
// ================================================================
function confirmDelete(formId, entityName) {
    if (confirm(`Are you sure you want to delete ${entityName || 'this record'}? This action cannot be undone.`)) {
        document.getElementById(formId)?.submit();
    }
    return false;
}

// ================================================================
// PREDICTION FORM - Dynamic field handling
// ================================================================
function handleInternetServiceChange() {
    const internetSvc = document.getElementById('internetService');
    if (!internetSvc) return;

    const internetOnlyFields = document.querySelectorAll('.internet-only');
    const updateFields = () => {
        const val = internetSvc.value;
        internetOnlyFields.forEach(el => {
            const select = el.querySelector('select');
            if (val === 'NO') {
                if (select) select.value = 'NO_INTERNET_SERVICE';
                el.style.opacity = '.5';
                el.style.pointerEvents = 'none';
            } else {
                el.style.opacity = '1';
                el.style.pointerEvents = 'auto';
                if (select && select.value === 'NO_INTERNET_SERVICE') select.value = 'NO';
            }
        });
    };
    internetSvc.addEventListener('change', updateFields);
    updateFields();
}

function handlePhoneServiceChange() {
    const phoneSvc = document.getElementById('phoneService');
    if (!phoneSvc) return;

    const multiLinesEl = document.getElementById('multipleLines');
    const updatePhoneFields = () => {
        if (!phoneSvc.checked) {
            if (multiLinesEl) { multiLinesEl.value = 'NO_PHONE_SERVICE'; multiLinesEl.disabled = true; }
        } else {
            if (multiLinesEl) { multiLinesEl.disabled = false; if (multiLinesEl.value === 'NO_PHONE_SERVICE') multiLinesEl.value = 'NO'; }
        }
    };
    phoneSvc.addEventListener('change', updatePhoneFields);
    updatePhoneFields();
}

// ================================================================
// CUSTOMER ID GENERATION
// ================================================================
function generateCustomerId() {
    const field = document.getElementById('customerId');
    if (!field || field.value) return;
    const prefix = 'CUST';
    const num = String(Math.floor(Math.random() * 90000) + 10000);
    field.value = `${prefix}-${num}`;
}

// ================================================================
// CHART HELPERS
// ================================================================
const CHART_COLORS = {
    primary: '#4f46e5',
    success: '#10b981',
    danger: '#ef4444',
    warning: '#f59e0b',
    info: '#3b82f6',
    secondary: '#06b6d4',
    purple: '#8b5cf6',
    pink: '#ec4899',
    palette: ['#4f46e5','#06b6d4','#10b981','#f59e0b','#ef4444','#8b5cf6','#ec4899','#14b8a6']
};

function getChartDefaults() {
    return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
            legend: {
                labels: {
                    font: { family: 'Inter', size: 12 },
                    color: getComputedStyle(document.documentElement).getPropertyValue('--text-secondary').trim()
                }
            },
            tooltip: {
                backgroundColor: '#1e293b',
                titleFont: { family: 'Inter', size: 12, weight: '600' },
                bodyFont: { family: 'Inter', size: 12 },
                padding: 10,
                cornerRadius: 8,
                titleColor: '#f1f5f9',
                bodyColor: '#94a3b8'
            }
        },
        scales: {
            x: {
                grid: { color: getComputedStyle(document.documentElement).getPropertyValue('--border').trim() || '#e2e8f0' },
                ticks: { font: { family: 'Inter', size: 11 }, color: '#64748b' }
            },
            y: {
                grid: { color: getComputedStyle(document.documentElement).getPropertyValue('--border').trim() || '#e2e8f0' },
                ticks: { font: { family: 'Inter', size: 11 }, color: '#64748b' },
                beginAtZero: true
            }
        }
    };
}

// ================================================================
// TABLE SEARCH DEBOUNCE
// ================================================================
let searchTimeout;
function debounceSearch(input, delayMs = 400) {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        const form = input.closest('form');
        if (form) form.submit();
    }, delayMs);
}

// ================================================================
// INIT
// ================================================================
document.addEventListener('DOMContentLoaded', () => {
    handleInternetServiceChange();
    handlePhoneServiceChange();

    // Auto-dismiss alerts after 5s
    document.querySelectorAll('.alert-auto-dismiss').forEach(el => {
        setTimeout(() => el.remove(), 5000);
    });

    // Smooth table row click navigation
    document.querySelectorAll('tr[data-href]').forEach(row => {
        row.style.cursor = 'pointer';
        row.addEventListener('click', (e) => {
            if (!e.target.closest('button, a, form')) window.location.href = row.dataset.href;
        });
    });
});
