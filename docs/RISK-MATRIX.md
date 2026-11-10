# Risk Matrix – Mobile Trading App (first public release)

Each functionality is scored from 1 (lowest) to 5 (highest) on:
- **Probability:** how likely it is to fail
- **Impact:** how badly users and the business are affected if it fails (real funds are involved)

**Priority** comes from Probability × Impact:
🔴 Critical (20–25) · 🟠 High (12–19) · 🟡 Medium (6–11) · 🟢 Low (1–5)

| Functionality | Probability | Impact | Score | Priority |
|---|---|---|---|---|
| Add money (deposit) | 4 | 5 | 20 | 🔴 Critical |
| Withdraw money | 4 | 5 | 20 | 🔴 Critical |
| Buy / sell orders | 4 | 5 | 20 | 🔴 Critical |
| Login and two-factor authentication | 4 | 5 | 20 | 🔴 Critical |
| Live price updates | 4 | 4 | 16 | 🟠 High |
| Balance and portfolio display | 3 | 5 | 15 | 🟠 High |
| Payment status after an interrupted transaction | 3 | 5 | 15 | 🟠 High |
| Registration and identity checks (KYC) | 3 | 4 | 12 | 🟠 High |
| Performance under peak load | 3 | 4 | 12 | 🟠 High |
| Users can only see their own data | 2 | 5 | 10 | 🟡 Medium |
| App stability (crashes) | 3 | 3 | 9 | 🟡 Medium |
| Transaction history | 3 | 3 | 9 | 🟡 Medium |
| Error logging and alerts | 3 | 3 | 9 | 🟡 Medium |
| Price alerts and push notifications | 2 | 2 | 4 | 🟢 Low |
| Update profile picture and settings | 2 | 1 | 2 | 🟢 Low |